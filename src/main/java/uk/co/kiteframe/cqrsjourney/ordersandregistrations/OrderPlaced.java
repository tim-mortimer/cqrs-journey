package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import java.util.Collection;

public class OrderPlaced extends DomainEvent {
    private final String orderId;
    private final String conferenceId;
    private final String userId;
    private final Collection<Seat> seats;

    public OrderPlaced(String orderId, String conferenceId, String userId, Collection<Seat> seats) {
        this.orderId = orderId;
        this.conferenceId = conferenceId;
        this.userId = userId;
        this.seats = seats;
    }

    public String orderId() {
        return orderId;
    }

    public String conferenceId() {
        return conferenceId;
    }

    public String userId() {
        return userId;
    }

    public Collection<Seat> seats() {
        return seats;
    }

    public record Seat(String seatTypeId, int quantity) {
    }
}
