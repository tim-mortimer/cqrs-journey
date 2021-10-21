package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

public interface OrderRepository {
    Order orderOfId(String s);

    void save(Order order);
}
