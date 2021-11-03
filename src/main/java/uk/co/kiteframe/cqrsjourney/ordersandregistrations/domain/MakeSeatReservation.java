package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

import uk.co.kiteframe.cqrsjourney.common.Command;

public record MakeSeatReservation(String conferenceId, String reservationId, int numberOfSeats) implements Command {
}
