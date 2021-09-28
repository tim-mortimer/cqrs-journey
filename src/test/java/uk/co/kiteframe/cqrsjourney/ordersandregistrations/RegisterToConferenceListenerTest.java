package uk.co.kiteframe.cqrsjourney.ordersandregistrations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.kiteframe.cqrsjourney.CommandBus;

import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RegisterToConferenceListenerTest {

    private final static String QUEUE_NAME = "registerToConference";
    private final static String ORDER_ID = "eff6ff2b-b691-4392-992d-8efde72c4b99";
    private final static String CONFERENCE_ID = "fb2c897d-7809-4ef3-83a3-5c1518cb1090";
    private final static String SEAT_TYPE_ID = "db9df4c5-d149-4f5f-b1b4-b67461b69e17";

    @Autowired
    CommandBus commandBus;

    @MockBean
    RegisterToConferenceCommandHandler commandHandler;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    RabbitListenerEndpointRegistry endpointRegistry;

    @Autowired
    AmqpAdmin amqpAdmin;

    @BeforeEach
    void beforeEach() {
        amqpAdmin.purgeQueue(QUEUE_NAME, true);
    }

    @AfterEach
    void afterEach() {
        amqpAdmin.purgeQueue(QUEUE_NAME, true);
        endpointRegistry.stop();
    }

    @Test
    void it_listens_to_register_to_conference_commands() {
        commandBus.send(new RegisterToConference(
                ORDER_ID,
                CONFERENCE_ID,
                List.of(
                        new RegisterToConference.SeatQuantity(
                                SEAT_TYPE_ID,
                                2
                        )
                )
        ));

        awaitForMessageToBeQueued();
        startListener();
        awaitForQueueToBeEmpty();

        verify(commandHandler).handle(
                new RegisterToConference(
                        ORDER_ID,
                        CONFERENCE_ID,
                        List.of(
                                new RegisterToConference.SeatQuantity(
                                        SEAT_TYPE_ID,
                                        2
                                )
                        )
                )
        );
    }

    private void awaitForMessageToBeQueued() {
        await().until(() -> {
            System.out.println(amqpAdmin.getQueueInfo(QUEUE_NAME).getMessageCount());
            return amqpAdmin.getQueueInfo(QUEUE_NAME).getMessageCount() == 1;
        });
    }

    private void startListener() {
        endpointRegistry.getListenerContainer("registerToConference").start();
    }

    private void awaitForQueueToBeEmpty() {
        await().until(() -> amqpAdmin.getQueueInfo(QUEUE_NAME).getMessageCount() == 0);
    }
}
