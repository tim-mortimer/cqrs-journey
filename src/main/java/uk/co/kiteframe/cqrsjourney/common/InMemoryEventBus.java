package uk.co.kiteframe.cqrsjourney.common;

import org.springframework.stereotype.Component;

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

    public List<DomainEvent> sent() {
        return sent;
    }
}
