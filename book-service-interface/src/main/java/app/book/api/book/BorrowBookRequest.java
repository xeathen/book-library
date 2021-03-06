package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
public class BorrowBookRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "expected_return_time")
    public LocalDateTime expectedReturnTime;
}
