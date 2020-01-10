package app.notification;

import app.book.api.kafka.BorrowedRecordExpirationMessage;
import app.book.api.kafka.ReservationAvailabilityMessage;
import app.notification.kafka.RecordExpirationMassageHandler;
import app.notification.kafka.ReservationMessageHandler;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        configureKafka();
    }

    private void configureKafka() {
        kafka().subscribe("reservation-availability", ReservationAvailabilityMessage.class, bind(ReservationMessageHandler.class));
        kafka().subscribe("borrowed-record-expiration", BorrowedRecordExpirationMessage.class, bind(RecordExpirationMassageHandler.class));
    }
}
