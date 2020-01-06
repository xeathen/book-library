package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class BorrowBookRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @NotNull
    @Property(name = "expected_return_time")
    public ZonedDateTime expectedReturnTime;
}
