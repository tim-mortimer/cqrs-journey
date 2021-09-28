package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RegisterToConferenceListener {

    private final RegisterToConferenceCommandHandler handler;

    public RegisterToConferenceListener(RegisterToConferenceCommandHandler handler) {
        this.handler = handler;
    }

    @RabbitListener(id = "registerToConference", queues = "registerToConference")
    private void handle(RegisterToConference command) {
        handler.handle(command);
    }
}
