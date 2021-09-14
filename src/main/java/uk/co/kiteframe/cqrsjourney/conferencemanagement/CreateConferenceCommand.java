package uk.co.kiteframe.cqrsjourney.conferencemanagement;

public record CreateConferenceCommand(String code, String title,
                                      String description) {
}
