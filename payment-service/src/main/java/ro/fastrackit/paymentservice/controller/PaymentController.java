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
import ro.fastrackit.paymentservice.service.PaymentService;
import ro.fasttrackit.payments.client.dto.Payment;

@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
public class PaymentController {
    private final PaymentService service;
    private final PaymentMapper mapper;

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
        return mapper.toApi(paymentEntity);
    }
}
