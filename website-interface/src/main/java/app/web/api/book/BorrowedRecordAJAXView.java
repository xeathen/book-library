package app.web.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class BorrowedRecordAJAXView {
    @Property(name = "id")
    public String id;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "book_name")
    public String bookName;

    @Property(name = "borrow_time")
    public ZonedDateTime borrowTime;

    @Property(name = "expected_return_time")
    public ZonedDateTime expectedReturnTime;

    @Property(name = "actual_return_time")
    public ZonedDateTime actualReturnTime;
}
