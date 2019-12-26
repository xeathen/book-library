package app.library.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class BorrowBookAJAXResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "borrow_time")
    public ZonedDateTime borrowTime;

    @Property(name = "return_time")
    public ZonedDateTime returnTime;
}
