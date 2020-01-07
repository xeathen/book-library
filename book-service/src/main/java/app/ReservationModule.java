package app;

import app.book.api.kafka.ExpirationMessage;
import app.book.api.kafka.ReservationMessage;
import app.book.service.ReservationService;
import app.job.AvailabilityNotificationJob;
import app.job.ExpirationNotificationJob;
import core.framework.module.Module;

import java.time.Duration;

/**
 * @author Ethan
 */
public class ReservationModule extends Module {
    @Override
    protected void initialize() {
        configureKafka();

        bind(ReservationService.class);
        schedule().fixedRate("availability-notify-job", bind(AvailabilityNotificationJob.class), Duration.ofHours(1));
        schedule().fixedRate("expiration-notify-job", bind(ExpirationNotificationJob.class), Duration.ofDays(1));
    }

    private void configureKafka() {
        kafka().publish("reservation", ReservationMessage.class);
        kafka().publish("expiration", ExpirationMessage.class);
    }
}
