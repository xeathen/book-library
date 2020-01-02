package app.kafka;

import app.book.api.kafka.ExpirationMessage;
import app.user.api.UserWebService;
import app.user.api.user.GetUserResponse;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import core.framework.log.ActionLogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ethan
 */
public class ExpireMassageHandler implements MessageHandler<ExpirationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ExpireMassageHandler.class);
    @Inject
    UserWebService userWebService;

    @Override
    public void handle(String key, ExpirationMessage value) throws Exception {
        ActionLogContext.put("userId", value.userId);
        ActionLogContext.put("bookId", value.bookId);
        GetUserResponse user = userWebService.get(value.userId);
        logger.info("Sending expiration email, userName={},userEmail={}", user.userName, user.email);
    }
}
