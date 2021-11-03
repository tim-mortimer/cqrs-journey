package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class ResgistrationProcessManagerTest {

    private final static String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final static String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
    private final static String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
    private final static String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

    @Test
    void the_registration_process_is_started_when_an_order_is_placed() {
        var processManager = new RegistrationProcessManager();
        processManager.handle(new OrderPlaced(ORDER_ID, USER_ID, CONFERENCE_ID, List.of(
                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
        )));

        assertThat(processManager.orderId()).isEqualTo(ORDER_ID);
        assertThatNoException().isThrownBy(() -> UUID.fromString(processManager.reservationId()));
        assertThat(processManager.state()).isEqualTo(RegistrationProcessManager.ProcessState.AWAITING_RESERVATION_CONFIRMATION);
    }

    @Test
    void a_make_seat_reservation_command_is_issued_when_an_order_is_placed() {
        var processManager = new RegistrationProcessManager();
        var event = new OrderPlaced(ORDER_ID, CONFERENCE_ID, USER_ID, List.of(
                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
        ));
        processManager.handle(event);

        assertThat(processManager.commands()).containsExactly(new MakeSeatReservation(
                CONFERENCE_ID,
                processManager.reservationId(),
                3
        ));
    }

    @Test
    void should_not_handle_order_placed_events_when_not_in_not_started_state() {
        var processManager = new RegistrationProcessManager();
        processManager.handle(new OrderPlaced(ORDER_ID, USER_ID, CONFERENCE_ID, List.of(
                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
        )));

        assertThat(processManager.state()).isEqualTo(RegistrationProcessManager.ProcessState.AWAITING_RESERVATION_CONFIRMATION);

        assertThatThrownBy(() -> processManager.handle(new OrderPlaced(ORDER_ID, USER_ID, CONFERENCE_ID, List.of(
                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
        )))).isInstanceOf(IllegalStateException.class);
    }
}
