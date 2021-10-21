package uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.Order;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderRepository;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure.GetOrderController;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure.OrderModel;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetOrderController.class)
public class GetOrderControllerTest {

    private final static String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final static String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
    private final static String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
    private final static String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

    @MockBean
    OrderRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void retrieving_an_order_by_id() throws Exception {
        when(repository.orderOfId(ORDER_ID)).thenReturn(new Order(
                ORDER_ID,
                USER_ID,
                CONFERENCE_ID,
                List.of(
                        new Order.OrderItem(SEAT_TYPE_1_ID, 2),
                        new Order.OrderItem(SEAT_TYPE_2_ID, 1)
                )
        ));

        mockMvc.perform(get("/ordersandregistrations/orders/" + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderModel(
                        ORDER_ID,
                        USER_ID,
                        CONFERENCE_ID,
                        List.of(
                                new OrderModel.OrderItemModel(SEAT_TYPE_1_ID, 2),
                                new OrderModel.OrderItemModel(SEAT_TYPE_2_ID, 1)
                        )
                ))));
    }
}
