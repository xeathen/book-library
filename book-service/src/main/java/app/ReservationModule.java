package app;

import app.book.api.kafka.ReservationMessage;
import app.job.NotifyJob;
import core.framework.module.Module;

import java.time.Duration;

/**
 * @author Ethan
 */
public class ReservationModule extends Module {
    @Override
    protected void initialize() {
        configureKafka();
        schedule().fixedRate("notify-job", bind(NotifyJob.class), Duration.ofDays(1));
    }

    private void configureKafka() {
        kafka().uri("localhost:9092");
        String topic = "reservation";
        kafka().poolSize(2);
        kafka().publish(topic, ReservationMessage.class);
    }
}
