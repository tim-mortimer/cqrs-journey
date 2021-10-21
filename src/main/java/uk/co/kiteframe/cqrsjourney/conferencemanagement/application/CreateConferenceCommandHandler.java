package uk.co.kiteframe.cqrsjourney.conferencemanagement.application;

import org.springframework.stereotype.Service;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.domain.Conference;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.domain.ConferenceRepository;

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
