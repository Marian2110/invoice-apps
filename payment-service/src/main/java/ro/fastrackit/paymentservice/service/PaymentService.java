package ro.fastrackit.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fastrackit.paymentservice.model.entity.PaymentEntity;
import ro.fastrackit.paymentservice.model.mapper.PaymentMapper;
import ro.fastrackit.paymentservice.repository.PaymentRepository;
import ro.fasttrackit.payments.client.dto.Payment;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public Page<PaymentEntity> getPayments(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<PaymentEntity> patchPayment(final String id, final JsonPatch jsonPatch) {
        return repository.findById(id)
                .map(dbEntity ->  applyPatch(dbEntity, jsonPatch))
                .map(dbEntity -> replaceEntity(id, dbEntity))
                .orElse(null);

    }

    public PaymentEntity replaceEntity(String id, PaymentEntity newEntity) {
        PaymentEntity dbEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find person with id %s".formatted(id)));
//        return repository.save(dbEntity.toBuilder()
//                .age(newEntity.getAge())
//                .name(newEntity.getName())
//                .build());

        return repository.save(dbEntity
                .withNewName(newEntity.getNewName())
                .withAge(newEntity.getAge()));
    }
    private PaymentEntity applyPatch(PaymentEntity dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.convertValue(mapper.toApi(dbEntity), JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            return mapper.toEntity(jsonMapper.treeToValue(patchedJson, Payment.class));
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }


}
