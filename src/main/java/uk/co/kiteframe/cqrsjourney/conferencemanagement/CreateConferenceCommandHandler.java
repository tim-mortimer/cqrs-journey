package uk.co.kiteframe.cqrsjourney.conferencemanagement;

public class CreateConferenceCommandHandler {
    private ConferenceRepository repository;

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
