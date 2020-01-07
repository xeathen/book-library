package app.web.api.book;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class ReturnBackBookAJAXResponse {
    @Property(name = "actual_return_time")
    public ZonedDateTime actualReturnTime;
}
