package uk.co.kiteframe.cqrsjourney.ordersandregistrations.infrastructure;

import uk.co.kiteframe.cqrsjourney.common.CommandBus;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManager;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManagerRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRegistrationProcessManagerRepository implements RegistrationProcessManagerRepository {
    Map<String, RegistrationProcessManager> processManagers;
    private final CommandBus commandBus;

    public InMemoryRegistrationProcessManagerRepository(CommandBus commandBus) {
        processManagers = new HashMap<>();
        this.commandBus = commandBus;
    }

    @Override
    public RegistrationProcessManager registrationOfReservationId(String reservationId) {
        return processManagers.get(reservationId);
    }

    @Override
    public void save(RegistrationProcessManager processManager) {
        processManagers.put(processManager.reservationId(), processManager);
        commandBus.send(processManager.commands().get(0));
    }
}
