package app;

import app.book.api.kafka.ExpireMessage;
import app.book.api.kafka.ReservationMessage;
import app.job.AvailabilityNotifyJob;
import app.job.ExpireNotifyJob;
import core.framework.module.Module;

import java.time.Duration;

/**
 * @author Ethan
 */
public class ReservationModule extends Module {
    @Override
    protected void initialize() {
        configureKafka();
        schedule().fixedRate("availability-notify-job", bind(AvailabilityNotifyJob.class), Duration.ofSeconds(30));
        schedule().fixedRate("expire-notify-job", bind(ExpireNotifyJob.class), Duration.ofSeconds(30));
    }

    private void configureKafka() {
        kafka().publish("reservation", ReservationMessage.class);
        kafka().publish("expire", ExpireMessage.class);
    }
}
