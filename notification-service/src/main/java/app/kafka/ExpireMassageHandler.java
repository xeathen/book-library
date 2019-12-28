package app.kafka;

import app.book.api.kafka.ExpireMessage;
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
public class ExpireMassageHandler implements MessageHandler<ExpireMessage> {
    private final Logger logger = LoggerFactory.getLogger(ReservationMessageHandler.class);
    @Inject
    UserWebService userWebService;

    @Override
    public void handle(String key, ExpireMessage value) throws Exception {
        ActionLogContext.put("user_id", value.userId);
        ActionLogContext.put("book_id", value.bookId);
        GetUserResponse user = userWebService.get(value.userId);
        logger.info("sending email, userName={},userEmail={}", user.userName, user.userEmail);
    }
}