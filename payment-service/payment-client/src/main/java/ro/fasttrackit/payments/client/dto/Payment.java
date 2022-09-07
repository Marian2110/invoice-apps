package ro.fasttrackit.payments.client.dto;

import lombok.Builder;
import ro.fasttrackit.payments.client.event.PaymentStatus;

@Builder(toBuilder = true)
public record Payment(
        String invoiceId,
        PaymentStatus status,
        Double payableAmount
) {
}
