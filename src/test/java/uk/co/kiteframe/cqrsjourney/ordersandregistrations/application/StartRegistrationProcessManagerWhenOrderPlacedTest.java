package uk.co.kiteframe.cqrsjourney.ordersandregistrations.application;

import org.junit.jupiter.api.Test;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderPlaced;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManagerRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StartRegistrationProcessManagerWhenOrderPlacedTest {

    @Test
    void should_coordinate_the_starting_of_the_registration_process() {
        RegistrationProcessManagerRepository repository = mock(RegistrationProcessManagerRepository.class);
        var policy = new StartRegistrationProcessManagerWhenOrderPlaced(repository);
        var event = anOrderPlacedEvent();
        policy.handle(event);
        verify(repository).save(argThat(processManager -> processManager.orderId().equals(event.orderId())));
    }

    private OrderPlaced anOrderPlacedEvent() {
        final String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
        final String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
        final String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
        final String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
        final String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

        return new OrderPlaced(ORDER_ID, CONFERENCE_ID, USER_ID, List.of(
                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
        ));
    }
}
