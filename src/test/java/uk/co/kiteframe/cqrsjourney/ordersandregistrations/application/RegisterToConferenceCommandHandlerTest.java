package uk.co.kiteframe.cqrsjourney.ordersandregistrations.application;

import org.junit.jupiter.api.Test;
import uk.co.kiteframe.cqrsjourney.common.InMemoryEventBus;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.application.RegisterToConference;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.application.RegisterToConferenceCommandHandler;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.Order;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderPlaced;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderRepository;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure.InMemoryOrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterToConferenceCommandHandlerTest {

    private final static String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final static String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
    private final static String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
    private final static String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

    @Test
    void registering_to_a_conference() {
        InMemoryEventBus eventBus = new InMemoryEventBus();
        OrderRepository orderRepository = new InMemoryOrderRepository(eventBus);
        var handler = new RegisterToConferenceCommandHandler(orderRepository);

        handler.handle(new RegisterToConference(
                ORDER_ID,
                CONFERENCE_ID,
                List.of(
                        new RegisterToConference.SeatQuantity(SEAT_TYPE_1_ID, 2),
                        new RegisterToConference.SeatQuantity(SEAT_TYPE_2_ID, 1)
                )
        ));

        var order = orderRepository.orderOfId(ORDER_ID);
        assertThat(order.id()).isEqualTo(ORDER_ID);
        assertThat(order.userId()).isEqualTo(USER_ID);
        assertThat(order.conferenceId()).isEqualTo(CONFERENCE_ID);
        assertThat(order.lines()).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new Order.OrderItem(SEAT_TYPE_1_ID, 2),
                        new Order.OrderItem(SEAT_TYPE_2_ID, 1)
                ));

        assertThat(eventBus.sent()).hasSize(1);
        OrderPlaced orderPlaced = (OrderPlaced) eventBus.sent().get(0);
        assertThat(orderPlaced.orderId()).isEqualTo(ORDER_ID);
        assertThat(orderPlaced.conferenceId()).isEqualTo(CONFERENCE_ID);
        assertThat(orderPlaced.userId()).isEqualTo(USER_ID);
        assertThat(orderPlaced.seats()).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                        new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
                ));
    }
}
