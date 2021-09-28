package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    Map<String, Order> orders = new HashMap<>();

    @Override
    public Order orderOfId(String orderId) {
        return orders.get(orderId);
    }

    @Override
    public void save(Order order) {
        orders.put(order.id(), order);
    }
}
