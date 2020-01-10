package app.web.api.book;

import core.framework.api.json.Property;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
public class BorrowBookAJAXResponse {
    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "book_name")
    public String bookName;

    @Property(name = "borrow_time")
    public LocalDateTime borrowTime;

    @Property(name = "expected_return_time")
    public LocalDateTime expectedReturnTime;
}
