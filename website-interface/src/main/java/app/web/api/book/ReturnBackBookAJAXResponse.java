package app.web.api.book;

import core.framework.api.json.Property;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
public class ReturnBackBookAJAXResponse {
    @Property(name = "actual_return_time")
    public LocalDateTime actualReturnTime;
}
