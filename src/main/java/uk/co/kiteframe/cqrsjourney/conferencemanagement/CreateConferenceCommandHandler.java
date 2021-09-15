package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import org.springframework.stereotype.Service;

@Service
public class CreateConferenceCommandHandler {
    private final ConferenceRepository repository;

    public CreateConferenceCommandHandler(ConferenceRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateConferenceCommand createConferenceCommand) {
        this.repository.save(new Conference(
                createConferenceCommand.code(),
                createConferenceCommand.title(),
                createConferenceCommand.description()
        ));
    }
}
