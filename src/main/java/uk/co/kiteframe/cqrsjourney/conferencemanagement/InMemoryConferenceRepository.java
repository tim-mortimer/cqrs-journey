package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryConferenceRepository implements ConferenceRepository {

    Map<String, Conference> conferences = new HashMap<>();

    @Override
    public Conference conferenceOfCode(String code) {
        return conferences.get(code);
    }

    @Override
    public void save(Conference conference) {
        conferences.put(conference.code(), conference);
    }
}
