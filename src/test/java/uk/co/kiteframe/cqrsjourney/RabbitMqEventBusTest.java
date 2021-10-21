package uk.co.kiteframe.cqrsjourney;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.DomainEvent;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.OrderPlaced;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RabbitMqEventBusTest extends EventBusTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    TopicExchange topicExchange;

    @Autowired
    Queue queue;

    @Autowired
    AmqpAdmin amqpAdmin;

    RabbitMqEventBus rabbitMqEventBus;

    @BeforeEach
    void beforeEach() {
        rabbitMqEventBus = new RabbitMqEventBus(amqpTemplate, topicExchange);
    }

    @Override
    EventBus getEventBus() {
        return rabbitMqEventBus;
    }

    @Override
    void assertSent(DomainEvent event) {
        var receivedEvent = amqpTemplate.receiveAndConvert(queue.getName(), ParameterizedTypeReference.forType(OrderPlaced.class));
        assertThat(receivedEvent).usingRecursiveComparison()
                .isEqualTo(event);
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
                    .with("events.OrderPlaced");
        }
    }
}
