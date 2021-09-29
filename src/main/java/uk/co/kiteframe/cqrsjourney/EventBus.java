package uk.co.kiteframe.cqrsjourney;

import org.springframework.stereotype.Component;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.DomainEvent;

import java.util.List;

@Component
public class EventBus {
    public void dispatch(List<DomainEvent> events) {
    }
}
