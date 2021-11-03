package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

import uk.co.kiteframe.cqrsjourney.common.Command;

import java.util.List;
import java.util.UUID;

public class RegistrationProcessManager {
    private String orderId;
    private String reservationId;
    private ProcessState state;
    private List<Command> commands;

    public RegistrationProcessManager() {
        state = ProcessState.NOT_STARTED;
    }

    public void handle(OrderPlaced orderPlaced) {
        if (state != ProcessState.NOT_STARTED) {
            throw new IllegalStateException();
        }

        orderId = orderPlaced.orderId();
        reservationId = UUID.randomUUID().toString();
        state = ProcessState.AWAITING_RESERVATION_CONFIRMATION;

        commands = List.of(new MakeSeatReservation(
                orderPlaced.conferenceId(),
                reservationId,
                orderPlaced.seats().stream().mapToInt(OrderPlaced.Seat::quantity).sum()));
    }

    public String orderId() {
        return orderId;
    }

    public String reservationId() {
        return reservationId;
    }

    public ProcessState state() {
        return state;
    }

    public List<Command> commands() {
        return commands;
    }

    public enum ProcessState {
        NOT_STARTED,
        AWAITING_RESERVATION_CONFIRMATION
    }
}
