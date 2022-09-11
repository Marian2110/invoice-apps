package ro.fastrackit.paymentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fastrackit.paymentservice.model.entity.PaymentEntity;

public interface PaymentRepository extends MongoRepository<PaymentEntity, String> {
}
