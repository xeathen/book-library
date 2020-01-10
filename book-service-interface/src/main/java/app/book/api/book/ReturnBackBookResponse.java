package app.book.api.book;

import core.framework.api.json.Property;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
public class ReturnBackBookResponse {
    @Property(name = "actual_return_time")
    public LocalDateTime actualReturnTime;
}
