package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

import uk.co.kiteframe.cqrsjourney.common.DomainEvent;

import java.util.Collection;

public record OrderPlaced(String orderId, String conferenceId, String userId,
                          Collection<Seat> seats) implements DomainEvent {

    public record Seat(String seatTypeId, int quantity) {
    }
}
