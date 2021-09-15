package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetConferenceController.class)
public class GetConferenceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ConferenceRepository repository;

    @Test
    void getting_a_conference_by_its_code() throws Exception {
        when(repository.conferenceOfCode(any())).thenReturn(new Conference(
                "TEST_CONFERENCE",
                "Test Conference",
                "Test Conference Description"
        ));

        mockMvc.perform(get("/conferencemanagement/conferences/TEST_CONFERENCE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(objectMapper.writeValueAsString(new ConferenceModel(
                        "TEST_CONFERENCE",
                        "Test Conference",
                        "Test Conference Description"
                ))));
    }
}
