package uk.co.kiteframe.cqrsjourney.conferencemanagement;

public interface ConferenceRepository {
    Conference conferenceOfCode(String code);

    void save(Conference conference);
}
