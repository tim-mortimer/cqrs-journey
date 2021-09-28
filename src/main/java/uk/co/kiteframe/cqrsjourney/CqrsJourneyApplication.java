package uk.co.kiteframe.cqrsjourney;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CqrsJourneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsJourneyApplication.class, args);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topic.exchange");
    }

    @Bean
    Queue registerToConferenceCommandQueue() {
        return new Queue("registerToConference");
    }

    @Bean
    Binding registerToConferenceQueueBinding(TopicExchange exchange) {
        return BindingBuilder.bind(registerToConferenceCommandQueue())
                .to(exchange)
                .with("commands.registerToConference");
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
