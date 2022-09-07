package ro.fasttrackit.payments.client;

import org.springframework.web.client.RestTemplate;
import ro.fasttrackit.payments.client.dto.Payment;

public class PaymentApiClient {
    private final String url;

    public PaymentApiClient(String url) {
        this.url = url;
    }

    public Payment add(Payment payment) {
        RestTemplate rest = new RestTemplate();
        return rest.postForObject(
                url + "/payments",
                payment,
                Payment.class);
    }
}
