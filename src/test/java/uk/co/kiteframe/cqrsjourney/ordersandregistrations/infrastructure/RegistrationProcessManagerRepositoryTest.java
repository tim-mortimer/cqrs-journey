package uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.kiteframe.cqrsjourney.common.Command;
import uk.co.kiteframe.cqrsjourney.common.CommandBus;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.MakeSeatReservation;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderPlaced;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManager;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManagerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RegistrationProcessManagerRepositoryTest {
    private final static String ORDER_ID = "fb46052b-df2e-43ca-a6fa-c974ab9ae770";
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final static String CONFERENCE_ID = "15992be9-64a0-41f0-8254-ce4c8df57e9d";
    private final static String SEAT_TYPE_1_ID = "7b773fc7-5a6e-4c99-af83-6e666437d184";
    private final static String SEAT_TYPE_2_ID = "c15ab856-6251-40ad-9ffb-defb18bf5dd0";

    @Test
    public void saving_and_retrieving_a_process_manager() {
        var processManager = new RegistrationProcessManager();
        processManager.handle(new OrderPlaced(ORDER_ID, CONFERENCE_ID, USER_ID, List.of(
                        new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                        new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
                )));

        CommandBus commandBus = mock(CommandBus.class);
        RegistrationProcessManagerRepository repository = new InMemoryRegistrationProcessManagerRepository(commandBus);

        repository.save(processManager);

        var retrievedProcessManager = repository.registrationOfReservationId(processManager.reservationId());

        assertThat(retrievedProcessManager.orderId()).isEqualTo(ORDER_ID);
        assertThat(retrievedProcessManager.reservationId()).isEqualTo(processManager.reservationId());
        assertThat(retrievedProcessManager.state()).isEqualTo(RegistrationProcessManager.ProcessState.AWAITING_RESERVATION_CONFIRMATION);
    }

    @Test
    void should_send_commands_to_the_command_bus() {
        var processManager = new RegistrationProcessManager();
        processManager.handle(new OrderPlaced(ORDER_ID, CONFERENCE_ID, USER_ID, List.of(
                new OrderPlaced.Seat(SEAT_TYPE_1_ID, 2),
                new OrderPlaced.Seat(SEAT_TYPE_2_ID, 1)
        )));

        CommandBus commandBus = mock(CommandBus.class);
        RegistrationProcessManagerRepository repository = new InMemoryRegistrationProcessManagerRepository(commandBus);

        repository.save(processManager);

        ArgumentCaptor<Command> argumentCaptor = ArgumentCaptor.forClass(Command.class);
        verify(commandBus).send(argumentCaptor.capture());
        MakeSeatReservation command = (MakeSeatReservation) argumentCaptor.getValue();
        assertThat(command.conferenceId()).isEqualTo(CONFERENCE_ID);
        assertThat(command.reservationId()).isEqualTo(processManager.reservationId());
        assertThat(command.numberOfSeats()).isEqualTo(3);
    }
}
