package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.kiteframe.cqrsjourney.CommandBus;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterToConferenceController.class)
public class RegisterToConferenceControllerTest {

    private final static String ORDER_ID = "eff6ff2b-b691-4392-992d-8efde72c4b99";
    private final static String CONFERENCE_ID = "fb2c897d-7809-4ef3-83a3-5c1518cb1090";
    private final static String SEAT_TYPE_ID = "db9df4c5-d149-4f5f-b1b4-b67461b69e17";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommandBus commandBus;

    @Test
    void registering_to_a_conference() throws Exception {
        mockMvc.perform(post("/ordersandregistrations/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new RegisterToConference(
                                ORDER_ID,
                                CONFERENCE_ID,
                                List.of(
                                        new RegisterToConference.SeatQuantity(
                                                SEAT_TYPE_ID,
                                                2
                                        )
                                )
                        )
                )))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()));

        verify(commandBus).send(new RegisterToConference(
                ORDER_ID,
                CONFERENCE_ID,
                List.of(
                        new RegisterToConference.SeatQuantity(
                                SEAT_TYPE_ID,
                                2
                        )
                )
        ));
    }
}
