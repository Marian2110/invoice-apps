package ro.fastrackit.paymentservice.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.fasttrackit.payments.client.event.PaymentEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {
    private final FanoutExchange fanoutExchange;
    private final RabbitTemplate rabbit;

    public void publishFinalizedPaymentFanout(PaymentEvent event) {
        log.info("publishing finalized payment fanout %s".formatted(event));
        rabbit.convertAndSend(fanoutExchange.getName(), "", event);
    }
}
