package uk.co.kiteframe.cqrsjourney.common;

import java.util.List;

public interface EventBus {
    void dispatch(List<DomainEvent> events);
}
