package uk.co.kiteframe.cqrsjourney.common;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import uk.co.kiteframe.cqrsjourney.common.CommandBus;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.application.RegisterToConference;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class CommandBusTest {
    private final static String ORDER_ID = "eff6ff2b-b691-4392-992d-8efde72c4b99";
    private final static String CONFERENCE_ID = "fb2c897d-7809-4ef3-83a3-5c1518cb1090";
    private final static String SEAT_TYPE_ID = "db9df4c5-d149-4f5f-b1b4-b67461b69e17";

    @Autowired
    TopicExchange exchange;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    Queue queue;

    @Test
    void sending_a_command() {
        CommandBus commandBus = new CommandBus(amqpTemplate, exchange);
        commandBus.send(new RegisterToConference(
                ORDER_ID,
                CONFERENCE_ID,
                List.of(
                        new RegisterToConference.SeatQuantity(
                                SEAT_TYPE_ID,
                                2
                        )
                )
        ));

        await().until(() -> amqpAdmin.getQueueInfo(queue.getName()).getMessageCount() == 1);

        RegisterToConference queuedMessage = amqpTemplate.receiveAndConvert(
                queue.getName(),
                ParameterizedTypeReference.forType(RegisterToConference.class));

        assertThat(queuedMessage).isNotNull();
        assertThat(queuedMessage.orderId()).isEqualTo(ORDER_ID);
        assertThat(queuedMessage.conferenceId()).isEqualTo(CONFERENCE_ID);
        assertThat(queuedMessage.seats()).hasSize(1);
        assertThat(queuedMessage.seats().get(0).typeId()).isEqualTo(SEAT_TYPE_ID);
        assertThat(queuedMessage.seats().get(0).quantity()).isEqualTo(2);
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration {

        @Bean
        Queue queue() {
            return new AnonymousQueue();
        }

        @Bean
        Binding binding(TopicExchange exchange, Queue queue) {
            return BindingBuilder.bind(queue)
                    .to(exchange)
                    .with("commands.registerToConference");
        }
    }
}
