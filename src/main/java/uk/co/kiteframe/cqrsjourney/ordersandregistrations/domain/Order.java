package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

import uk.co.kiteframe.cqrsjourney.common.DomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private final String id;
    private final String userId;
    private final String conferenceId;
    private final List<OrderItem> lines;
    private final List<DomainEvent> events;

    public Order(String id, String userId, String conferenceId, List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
        this.conferenceId = conferenceId;
        this.lines = orderItems;
        this.events = new ArrayList<>();
        this.events.add(new OrderPlaced(
                id,
                conferenceId,
                userId,
                orderItems.stream()
                        .map(orderItem -> new OrderPlaced.Seat(orderItem.seatTypeId(), orderItem.quantity()))
                        .collect(Collectors.toList())));
    }

    public String id() {
        return id;
    }

    public String userId() {
        return userId;
    }

    public String conferenceId() {
        return conferenceId;
    }

    public Collection<OrderItem> lines() {
        return lines;
    }

    public List<DomainEvent> events() {
        return events;
    }

    public void flushEvents() {
        events.clear();
    }

    public static record OrderItem(String seatTypeId, int quantity) {
    }
}
