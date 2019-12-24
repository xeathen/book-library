package app.book.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class BorrowBookRequest {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "return_time")
    public ZonedDateTime returnTime;
}
