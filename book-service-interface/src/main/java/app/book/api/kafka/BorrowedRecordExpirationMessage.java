package app.book.api.kafka;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class BorrowedRecordExpirationMessage {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "book_id")
    public Long bookId;
}
