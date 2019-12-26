package app.notification;

import app.book.api.kafka.ReservationMessage;
import app.kafka.ReservationMessageHandler;
import app.user.api.UserWebService;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        configureKafka();
        api().client(UserWebService.class, "app.userWebService.URL");
        bind(UserWebService.class);
    }

    private void configureKafka() {
        kafka().uri("localhost:9092");
        String topic = "reservation";
        kafka().subscribe(topic, ReservationMessage.class, bind(ReservationMessageHandler.class));
        kafka().poolSize(2);
    }
}
