package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import org.springframework.stereotype.Repository;
import uk.co.kiteframe.cqrsjourney.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    Map<String, Order> orders = new HashMap<>();
    EventBus eventBus;

    public InMemoryOrderRepository(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Order orderOfId(String orderId) {
        return orders.get(orderId);
    }

    @Override
    public void save(Order order) {
        orders.put(order.id(), order);
        eventBus.dispatch(List.copyOf(order.events()));
        order.flushEvents();
    }
}
