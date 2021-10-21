package uk.co.kiteframe.cqrsjourney;

import org.junit.jupiter.api.BeforeEach;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.DomainEvent;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryEventBusTest extends EventBusTest {

    InMemoryEventBus inMemoryEventBus;

    @BeforeEach
    void beforeEach() {
        inMemoryEventBus = new InMemoryEventBus();
    }

    @Override
    EventBus getEventBus() {
        return inMemoryEventBus;
    }

    @Override
    void assertSent(DomainEvent event) {
        assertThat(inMemoryEventBus.sent()).contains(event);
    }
}
