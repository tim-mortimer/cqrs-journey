package uk.co.kiteframe.cqrsjourney.conferencemanagement.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.application.CreateConferenceCommand;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.application.CreateConferenceCommandHandler;

@RestController
public class CreateConferenceController {

    private final CreateConferenceCommandHandler handler;

    public CreateConferenceController(CreateConferenceCommandHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/conferencemanagement/conferences")
    @ResponseStatus(HttpStatus.CREATED)
    void createConference(@RequestBody CreateConferenceCommand command) {
        handler.handle(command);
    }
}
