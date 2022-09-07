package ro.fasttrackit.payments.client.event;

import lombok.Builder;
import ro.fasttrackit.payments.client.dto.Payment;

@Builder
public record PaymentEvent(
        PaymentStatus status,
        Payment payment
) {
}
