package app.kafka;

import app.book.api.kafka.ReservationMessage;
import core.framework.kafka.MessageHandler;

/**
 * @author Ethan
 */
public class ReservationMessageHandler implements MessageHandler<ReservationMessage> {
    @Override
    public void handle(String key, ReservationMessage value) throws Exception {

    }
}
