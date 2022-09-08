package ro.fastrackit.paymentservice.exception.custom;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
