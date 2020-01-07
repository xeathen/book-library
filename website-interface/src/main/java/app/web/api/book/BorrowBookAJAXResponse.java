package app.web.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
//TODO:检查response是否多余
public class BorrowBookAJAXResponse {
    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "book_name")
    public String bookName;

    @Property(name = "borrow_time")
    public ZonedDateTime borrowTime;

    @Property(name = "expected_return_time")
    public ZonedDateTime expectedReturnTime;
}
