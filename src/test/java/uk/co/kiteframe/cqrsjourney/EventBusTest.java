package uk.co.kiteframe.cqrsjourney;

import org.junit.jupiter.api.Test;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.OrderPlaced;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class EventBusTest {

    private final static String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final static String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
    private final static String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
    private final static String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

    abstract EventBus getEventBus();

    @Test
    void publishing_an_event() {
        EventBus eventBus = getEventBus();

        eventBus.dispatch(List.of(new OrderPlaced(
                ORDER_ID,
                CONFERENCE_ID,
                USER_ID,
                List.of(
                        new OrderPlaced.Seat(SEAT_TYPE_1_ID, 1),
                        new OrderPlaced.Seat(SEAT_TYPE_2_ID, 2)
                )
        )));

        assertThat(eventBus.sent()).usingRecursiveComparison()
                .isEqualTo(List.of(new OrderPlaced(
                        ORDER_ID,
                        CONFERENCE_ID,
                        USER_ID,
                        List.of(
                                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 1),
                                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 2)
                        )
                )));
    }
}
