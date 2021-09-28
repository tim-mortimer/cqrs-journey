package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class GetOrderController {

    private final OrderRepository orderRepository;

    public GetOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/ordersandregistrations/orders/{orderId}")
    private OrderModel getOrder(@PathVariable String orderId) {
        var order = orderRepository.orderOfId(orderId);
        return new OrderModel(
                order.id(),
                order.userId(),
                order.conferenceId(),
                order.lines()
                        .stream()
                        .map(orderItem -> new OrderModel.OrderItemModel(
                                orderItem.seatTypeId(),
                                orderItem.quantity()))
                        .collect(Collectors.toList())
        );
    }
}
