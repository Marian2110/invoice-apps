package ro.fastrackit.paymentservice.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.fasttrackit.payments.client.event.PaymentStatus;

import java.time.LocalDateTime;

@Document(collection = "payments")
public record PaymentEntity(
        @Id
        String paymentId,
        PaymentStatus status,
        Double payableAmount,
        @CreatedDate
        LocalDateTime createdAt,
        @LastModifiedDate
        LocalDateTime updatedAt
) {

}
