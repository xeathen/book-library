package app.notification.kafka;

import app.book.api.kafka.BorrowedRecordExpirationMessage;
import core.framework.kafka.MessageHandler;
import core.framework.log.ActionLogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ethan
 */
public class RecordExpirationMassageHandler implements MessageHandler<BorrowedRecordExpirationMessage> {
    private final Logger logger = LoggerFactory.getLogger(RecordExpirationMassageHandler.class);

    @Override
    public void handle(String key, BorrowedRecordExpirationMessage value) {
        ActionLogContext.put("username", value.username);
        ActionLogContext.put("bookName", value.bookName);
        logger.info("Sending expiration email, username={}, userEmail={}, bookName={}", value.username, value.email, value.bookName);
    }
}
