package app.kafka;

import app.book.api.kafka.ReservationMessage;
import core.framework.kafka.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ethan
 */
public class ReservationMessageHandler implements MessageHandler<ReservationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ReservationMessageHandler.class);
    @Override
    public void handle(String key, ReservationMessage value) throws Exception {
        //TODO:Mock sending email to notify the user whose reservations will take effect soon.
        logger.info("sending email, userId:"+ value.userId);
    }
}
