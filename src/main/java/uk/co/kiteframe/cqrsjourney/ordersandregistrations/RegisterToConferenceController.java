package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.kiteframe.cqrsjourney.CommandBus;

@RestController
public class RegisterToConferenceController {

    private final CommandBus commandBus;

    public RegisterToConferenceController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/ordersandregistrations/orders")
    @ResponseStatus(HttpStatus.ACCEPTED)
    private void registerToConference(@RequestBody RegisterToConference command) {
        commandBus.send(command);
    }
}
