package app.library.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class CreateReservationAJAXResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "book_id")
    public Long bookId;
}
