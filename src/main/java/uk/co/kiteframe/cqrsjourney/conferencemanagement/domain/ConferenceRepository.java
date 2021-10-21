package uk.co.kiteframe.cqrsjourney.conferencemanagement.domain;

import uk.co.kiteframe.cqrsjourney.conferencemanagement.domain.Conference;

public interface ConferenceRepository {
    Conference conferenceOfCode(String code);

    void save(Conference conference);
}
