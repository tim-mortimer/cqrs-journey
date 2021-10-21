package uk.co.kiteframe.cqrsjourney.conferencemanagement.domain;

public class Conference {
    private final String code;
    private final String title;
    private final String description;

    public Conference(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public String code() {
        return code;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }
}
