package uk.co.kiteframe.cqrsjourney.conferencemanagement.application;

public record CreateConferenceCommand(String code, String title,
                                      String description) {
}
