package app.notification;

import app.book.api.kafka.ExpireMessage;
import app.book.api.kafka.ReservationMessage;
import app.kafka.ExpireMassageHandler;
import app.kafka.ReservationMessageHandler;
import app.user.api.UserWebService;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class  NotificationModule extends Module {
    @Override
    protected void initialize() {
        api().client(UserWebService.class, requiredProperty("app.userWebService.URL"));
        configureKafka();
    }

    private void configureKafka() {
        kafka().uri("localhost:9092");
        String reservationTopic = "reservation";
        String expireTopic = "expire";
        kafka().subscribe(expireTopic, ExpireMessage.class, bind(ExpireMassageHandler.class));
        kafka().subscribe(reservationTopic, ReservationMessage.class, bind(ReservationMessageHandler.class));
        kafka().poolSize(4);
    }
}
