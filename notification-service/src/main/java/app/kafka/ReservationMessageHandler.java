package app.kafka;

import app.book.api.kafka.ReservationMessage;
import app.user.api.BOUserWebService;
import app.user.api.user.BOGetUserResponse;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import core.framework.log.ActionLogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ethan
 */
public class ReservationMessageHandler implements MessageHandler<ReservationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ReservationMessageHandler.class);
    @Inject
    BOUserWebService boUserWebService;

    @Override
    public void handle(String key, ReservationMessage value) {
        ActionLogContext.put("userId", value.userId);
        ActionLogContext.put("bookId", value.bookId);
        BOGetUserResponse user = boUserWebService.get(value.userId);
        logger.info("Sending reservation email, userName={}, userEmail={}, bookId={}", user.userName, user.email, value.bookId);
    }
}
