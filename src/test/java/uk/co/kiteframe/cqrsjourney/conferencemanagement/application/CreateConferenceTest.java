package uk.co.kiteframe.cqrsjourney.conferencemanagement.application;

import org.junit.jupiter.api.Test;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.application.CreateConferenceCommand;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.application.CreateConferenceCommandHandler;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.domain.ConferenceRepository;
import uk.co.kiteframe.cqrsjourney.conferencemanagement.infrastructure.InMemoryConferenceRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateConferenceTest {

    @Test
    void creating_a_conference() {
        ConferenceRepository repository = new InMemoryConferenceRepository();
        CreateConferenceCommandHandler handler = new CreateConferenceCommandHandler(repository);
        handler.handle(new CreateConferenceCommand(
                "TEST_CONFERENCE",
                "Test Conference",
                "Test Conference Description"));

        var retrievedConference = repository.conferenceOfCode("TEST_CONFERENCE");
        assertThat(retrievedConference.code()).isEqualTo("TEST_CONFERENCE");
        assertThat(retrievedConference.title()).isEqualTo("Test Conference");
        assertThat(retrievedConference.description()).isEqualTo("Test Conference Description");
    }
}
