package uk.co.kiteframe.cqrsjourney;

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
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
