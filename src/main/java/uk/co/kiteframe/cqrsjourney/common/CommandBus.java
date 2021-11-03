package uk.co.kiteframe.cqrsjourney.common;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Component;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.application.RegisterToConference;

@Component
public class CommandBus {
    final AmqpTemplate amqpTemplate;
    final TopicExchange topicExchange;

    public CommandBus(AmqpTemplate amqpTemplate, TopicExchange topicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.topicExchange = topicExchange;
    }

    public void send(Command command) {
        String routingKey;

        if (command instanceof RegisterToConference) {
            routingKey = "commands.registerToConference";
        } else {
            routingKey = "commands.makeSeatReservation";
        }

        amqpTemplate.convertAndSend(
                topicExchange.getName(),
                routingKey,
                command);
    }
}
