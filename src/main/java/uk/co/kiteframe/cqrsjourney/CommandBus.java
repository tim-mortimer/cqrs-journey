package uk.co.kiteframe.cqrsjourney;

import org.springframework.stereotype.Component;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.RegisterToConference;

@Component
public class CommandBus {
    public void send(RegisterToConference registerToConference) {
    }
}
