package uk.co.kiteframe.cqrsjourney;

import org.springframework.stereotype.Component;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.DomainEvent;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryEventBus implements EventBus {

    private final List<DomainEvent> sent;

    public InMemoryEventBus() {
        this.sent = new ArrayList<>();
    }

    @Override
    public void dispatch(List<DomainEvent> events) {
        sent.addAll(events);
    }

    @Override
    public List<DomainEvent> sent() {
        return sent;
    }
}
