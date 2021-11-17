package uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.kiteframe.cqrsjourney.common.DomainEvent;
import uk.co.kiteframe.cqrsjourney.common.EventBus;
import uk.co.kiteframe.cqrsjourney.common.InMemoryEventBus;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.Order;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderPlaced;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure.InMemoryOrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrderRepositoryTest {

    private final static String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final static String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
    private final static String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
    private final static String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

    @Test
    void saving_and_retrieving_an_order() {
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository(new InMemoryEventBus());
        orderRepository.save(new Order(
                ORDER_ID,
                USER_ID,
                CONFERENCE_ID,
                List.of(
                        new Order.OrderItem(SEAT_TYPE_1_ID, 2),
                        new Order.OrderItem(SEAT_TYPE_2_ID, 1)
                )
        ));

        var retrievedOrder = orderRepository.orderOfId(ORDER_ID);
        assertThat(retrievedOrder.id()).isEqualTo(ORDER_ID);
        assertThat(retrievedOrder.userId()).isEqualTo(USER_ID);
        assertThat(retrievedOrder.conferenceId()).isEqualTo(CONFERENCE_ID);
        assertThat(retrievedOrder.lines()).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new Order.OrderItem(SEAT_TYPE_1_ID, 2),
                        new Order.OrderItem(SEAT_TYPE_2_ID, 1)
                ));
    }

    @Test
    void any_events_are_dispatched_to_the_event_bus() {
        EventBus eventBus = mock(EventBus.class);
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository(eventBus);
        orderRepository.save(Order.place(
                ORDER_ID,
                USER_ID,
                CONFERENCE_ID,
                List.of(
                        new Order.OrderItem(SEAT_TYPE_1_ID, 2),
                        new Order.OrderItem(SEAT_TYPE_2_ID, 1)
                )
        ));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<DomainEvent>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventBus).dispatch(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new OrderPlaced(
                                ORDER_ID,
                                CONFERENCE_ID,
                                USER_ID,
                                List.of(
                                        new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                                        new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
                                )
                        )
                ));
    }

    @Test
    void saving_an_order_flushes_its_events() {
        var eventBus = new InMemoryEventBus();
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository(eventBus);
        var order = anOrder();
        orderRepository.save(order);
        assertThat(order.events()).isEmpty();
    }

    private Order anOrder() {
        return new Order(
                ORDER_ID,
                USER_ID,
                CONFERENCE_ID,
                List.of(
                        new Order.OrderItem(SEAT_TYPE_1_ID, 2),
                        new Order.OrderItem(SEAT_TYPE_2_ID, 1)
                )
        );
    }
}
