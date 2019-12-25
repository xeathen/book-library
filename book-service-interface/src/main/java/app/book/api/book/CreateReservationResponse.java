package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class CreateReservationResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "book_id")
    public Long bookId;
}
