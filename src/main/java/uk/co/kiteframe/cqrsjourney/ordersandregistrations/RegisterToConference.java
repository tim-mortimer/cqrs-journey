package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import java.util.List;

public record RegisterToConference(String orderId, String conferenceId, List<SeatQuantity> seats) {
    public record SeatQuantity(String typeId, int quantity) { }
}
