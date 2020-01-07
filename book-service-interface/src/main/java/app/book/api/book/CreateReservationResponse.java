package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class CreateReservationResponse {
    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "book_name")
    public String bookName;
}
