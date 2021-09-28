package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import java.util.List;

public record OrderModel(String orderId, String userId, String conferenceId, List<OrderItemModel> lines) {
    public record OrderItemModel(String seatTypeId, int quantity) {
    }
}
