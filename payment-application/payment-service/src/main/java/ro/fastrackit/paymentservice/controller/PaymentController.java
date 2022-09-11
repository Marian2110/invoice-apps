package ro.fastrackit.paymentservice.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import page.CollectionResponse;
import page.PageInfo;
import ro.fastrackit.paymentservice.exception.custom.ResourceNotFoundException;
import ro.fastrackit.paymentservice.model.entity.PaymentEntity;
import ro.fastrackit.paymentservice.model.mapper.PaymentMapper;
import ro.fastrackit.paymentservice.queue.MessagePublisher;
import ro.fastrackit.paymentservice.service.PaymentService;
import ro.fasttrackit.payments.client.dto.Payment;
import ro.fasttrackit.payments.client.event.PaymentEvent;
import ro.fasttrackit.payments.client.event.PaymentStatus;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
public class PaymentController {
    private final PaymentService service;
    private final PaymentMapper mapper;
    private final MessagePublisher publisher;

    @GetMapping
    public CollectionResponse<Payment> getPayments(Pageable pageable) {
        final Page<PaymentEntity> payments = service.getPayments(pageable);
        return CollectionResponse.<Payment>builder()
                .items(mapper.toApi(payments.getContent()))
                .pageInfo(PageInfo.builder()
                        .page(payments.getNumber())
                        .size(payments.getSize())
                        .totalPages(payments.getTotalPages())
                        .totalSize((int) payments.getTotalElements())
                        .build())
                .build();
    }

    @PatchMapping("/{id}")
    public Payment patchPayment(@PathVariable final String id, @RequestBody final JsonPatch jsonPatch) {
        final PaymentEntity paymentEntity = service
                .patchPayment(id, jsonPatch)
                .orElseThrow(() -> ResourceNotFoundException.forEntity(Payment.class, id));

        checkStatus(paymentEntity);

        return mapper.toApi(paymentEntity);
    }

    private void checkStatus(final PaymentEntity entity) {
        Optional.of(entity)
                .filter(paymentEntity -> PaymentStatus.DONE.equals(paymentEntity.status()))
                .ifPresent(this::emitsFinalizedPaymentEvent);
    }


    private void emitsFinalizedPaymentEvent(final PaymentEntity paymentEntity) {
        publisher.publishFinalizedPaymentFanout(PaymentEvent.builder()
                .payment(mapper.toApi(paymentEntity))
                .status(paymentEntity.status())
                .build());
    }

}
