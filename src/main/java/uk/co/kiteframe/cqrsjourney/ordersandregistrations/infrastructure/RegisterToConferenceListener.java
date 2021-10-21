package uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.application.RegisterToConference;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.application.RegisterToConferenceCommandHandler;

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
