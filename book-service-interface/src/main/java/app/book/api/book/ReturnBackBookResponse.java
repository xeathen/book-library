package app.book.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class ReturnBackBookResponse {
    @Property(name = "actual_return_time")
    public ZonedDateTime actualReturnTime;
}
