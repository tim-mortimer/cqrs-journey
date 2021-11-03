package uk.co.kiteframe.cqrsjourney.ordersandregistrations.application;

import uk.co.kiteframe.cqrsjourney.common.Command;

import java.util.List;

public record RegisterToConference(String orderId, String conferenceId, List<SeatQuantity> seats) implements Command {
    public record SeatQuantity(String typeId, int quantity) { }
}
