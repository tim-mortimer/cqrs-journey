package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import java.util.Collection;

public record OrderPlaced(String orderId, String conferenceId, String userId,
                          Collection<Seat> seats) implements DomainEvent {

    public record Seat(String seatTypeId, int quantity) {
    }
}
