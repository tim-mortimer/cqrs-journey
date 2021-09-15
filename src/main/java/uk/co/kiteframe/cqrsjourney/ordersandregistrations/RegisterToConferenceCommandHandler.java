package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import java.util.stream.Collectors;

public class RegisterToConferenceCommandHandler {
    private final static String USER_ID = "e1236551-da3e-43ed-b600-a6e8a7e747e0";
    private final OrderRepository orderRepository;

    public RegisterToConferenceCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void handle(RegisterToConference command) {
        orderRepository.save(new Order(
                command.orderId(),
                USER_ID,
                command.conferenceId(),
                command.seats()
                        .stream()
                        .map(seat -> new Order.OrderItem(seat.typeId(), seat.quantity()))
                        .collect(Collectors.toList())
        ));
    }
}
