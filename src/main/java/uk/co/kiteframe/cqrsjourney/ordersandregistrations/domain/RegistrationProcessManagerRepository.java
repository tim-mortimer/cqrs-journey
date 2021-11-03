package uk.co.kiteframe.cqrsjourney.ordersandregistrations.domain;

public interface RegistrationProcessManagerRepository {
    RegistrationProcessManager registrationOfReservationId(String reservationId);

    void save(RegistrationProcessManager processManager);
}
