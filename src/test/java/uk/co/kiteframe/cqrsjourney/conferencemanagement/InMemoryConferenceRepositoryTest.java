package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryConferenceRepositoryTest {

    @Test
    void saving_and_retrieving_a_conference() {
        InMemoryConferenceRepository repository = new InMemoryConferenceRepository();
        repository.save(new Conference("TEST_CONFERENCE", "Test Conference", "Test Conference Description"));
        var retrievedConference = repository.conferenceOfCode("TEST_CONFERENCE");
        assertThat(retrievedConference)
                .usingRecursiveComparison()
                .isEqualTo(new Conference("TEST_CONFERENCE", "Test Conference", "Test Conference Description"));
    }
}
