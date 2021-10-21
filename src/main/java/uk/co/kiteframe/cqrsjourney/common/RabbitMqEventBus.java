package uk.co.kiteframe.cqrsjourney.common;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;

import java.util.List;

public class RabbitMqEventBus implements EventBus {

    private final AmqpTemplate amqpTemplate;
    private final TopicExchange topicExchange;

    public RabbitMqEventBus(AmqpTemplate amqpTemplate, TopicExchange topicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.topicExchange = topicExchange;
    }

    @Override
    public void dispatch(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            amqpTemplate.convertAndSend(
                    topicExchange.getName(),
                    "events.OrderPlaced",
                    event);
        }
    }
}
