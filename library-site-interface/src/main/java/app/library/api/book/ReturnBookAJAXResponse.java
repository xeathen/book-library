package app.library.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class ReturnBookAJAXResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "return_time")
    public ZonedDateTime returnTime;
}
