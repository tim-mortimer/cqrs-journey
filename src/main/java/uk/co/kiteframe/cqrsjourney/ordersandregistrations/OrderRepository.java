package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

public interface OrderRepository {
    Order orderOfId(String s);

    void save(Order order);
}
