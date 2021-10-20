package uk.co.kiteframe.cqrsjourney;

public class InMemoryEventBusTest extends EventBusTest {

    @Override
    EventBus getEventBus() {
        return new InMemoryEventBus();
    }
}
