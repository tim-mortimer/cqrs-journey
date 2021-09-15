package uk.co.kiteframe.cqrsjourney.conferencemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateConferenceController.class)
public class CreateConferenceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CreateConferenceCommandHandler handler;

    @Test
    void creating_a_conference() throws Exception {
        mockMvc.perform(post("/conferencemanagement/conferences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "code", "TEST_CONFERENCE",
                        "title", "Test Conference",
                        "description", "Test Conference Description"
                ))))
                .andExpect(status().is(HttpStatus.CREATED.value()));

        ArgumentCaptor<CreateConferenceCommand> argumentCaptor = ArgumentCaptor.forClass(CreateConferenceCommand.class);
        verify(handler).handle(argumentCaptor.capture());
        var command = argumentCaptor.getValue();
        assertThat(command).usingRecursiveComparison()
                .isEqualTo(new CreateConferenceCommand(
                        "TEST_CONFERENCE",
                        "Test Conference",
                        "Test Conference Description"
                ));
    }
}
