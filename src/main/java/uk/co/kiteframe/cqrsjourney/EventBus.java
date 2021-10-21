package uk.co.kiteframe.cqrsjourney;

import uk.co.kiteframe.cqrsjourney.ordersandregistrations.DomainEvent;

import java.util.List;

public interface EventBus {
    void dispatch(List<DomainEvent> events);
}
