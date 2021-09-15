package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetConferenceController {

    @Autowired
    ConferenceRepository repository;

    @GetMapping("/conferencemanagement/conferences/{code}")
    private ConferenceModel getConferenceByCode(@PathVariable String code) {
        var conference = repository.conferenceOfCode(code);
        return new ConferenceModel(
                conference.code(),
                conference.title(),
                conference.description());
    }
}
