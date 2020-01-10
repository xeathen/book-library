package app.notification.kafka;

import app.book.api.kafka.ReservationAvailabilityMessage;
import core.framework.kafka.MessageHandler;
import core.framework.log.ActionLogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ethan
 */
public class ReservationMessageHandler implements MessageHandler<ReservationAvailabilityMessage> {
    private final Logger logger = LoggerFactory.getLogger(ReservationMessageHandler.class);

    @Override
    public void handle(String key, ReservationAvailabilityMessage value) {
        ActionLogContext.put("username", value.username);
        ActionLogContext.put("bookId", value.bookName);
        logger.info("Sending reservation availability email, username={}, userEmail={}, bookName={}", value.username, value.email, value.bookName);
    }
}
