package uk.co.kiteframe.cqrsjourney;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Component;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.RegisterToConference;

@Component
public class CommandBus {
    final AmqpTemplate amqpTemplate;
    final TopicExchange topicExchange;

    public CommandBus(AmqpTemplate amqpTemplate, TopicExchange topicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.topicExchange = topicExchange;
    }

    public void send(RegisterToConference registerToConference) {
        amqpTemplate.convertAndSend(
                topicExchange.getName(),
                "commands.registerToConference",
                registerToConference);
    }
}
