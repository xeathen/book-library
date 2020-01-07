package app.notification.kafka;

import app.book.api.kafka.BorrowedRecordExpirationMessage;
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
public class ExpireMassageHandler implements MessageHandler<BorrowedRecordExpirationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ExpireMassageHandler.class);
    @Inject
    BOUserWebService boUserWebService;

    @Override
    public void handle(String key, BorrowedRecordExpirationMessage value) {
        ActionLogContext.put("userId", value.userId);
        ActionLogContext.put("bookId", value.bookId);
        BOGetUserResponse user = boUserWebService.get(value.userId);
        logger.info("Sending expiration email, userName={}, userEmail={}, bookId={}", user.userName, user.email, value.bookId);
    }
}