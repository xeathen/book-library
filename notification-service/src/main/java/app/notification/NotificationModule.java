package app.notification;

import app.book.api.kafka.ExpirationMessage;
import app.book.api.kafka.ReservationMessage;
import app.kafka.ExpireMassageHandler;
import app.kafka.ReservationMessageHandler;
import app.user.api.UserWebService;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        api().client(UserWebService.class, requiredProperty("app.userWebService.URL"));
        configureKafka();
    }

    private void configureKafka() {
        kafka().subscribe("reservation", ReservationMessage.class, bind(ReservationMessageHandler.class));
        kafka().subscribe("expiration", ExpirationMessage.class, bind(ExpireMassageHandler.class));
    }
}
