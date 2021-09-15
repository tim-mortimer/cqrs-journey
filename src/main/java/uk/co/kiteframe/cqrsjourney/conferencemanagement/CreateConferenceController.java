package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
