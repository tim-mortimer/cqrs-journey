package uk.co.kiteframe.cqrsjourney.ordersandregistrations.application;

import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.OrderPlaced;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManager;
import uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain.RegistrationProcessManagerRepository;

public class StartRegistrationProcessManagerWhenOrderPlaced {

    private final RegistrationProcessManagerRepository repository;

    public StartRegistrationProcessManagerWhenOrderPlaced(RegistrationProcessManagerRepository repository) {
        this.repository = repository;
    }

    public String handle(OrderPlaced orderPlaced) {
        var processManager = new RegistrationProcessManager();
        processManager.handle(orderPlaced);
        repository.save(processManager);
        return processManager.reservationId();
    }
}
