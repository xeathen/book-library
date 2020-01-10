package app.web.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
public class BorrowBookAJAXRequest {
    @NotNull
    @Property(name = "expected_return_time")
    public LocalDateTime expectedReturnTime;
}
