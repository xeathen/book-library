package app.bo.api.book;

import core.framework.api.json.Property;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
public class BorrowedRecordView {
    @Property(name = "id")
    public String id;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "username")
    public String username;

    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "book_name")
    public String bookName;

    @Property(name = "borrow_time")
    public LocalDateTime borrowTime;

    @Property(name = "expected_return_time")
    public LocalDateTime expectedReturnTime;

    @Property(name = "actual_return_time")
    public LocalDateTime actualReturnTime;
}
