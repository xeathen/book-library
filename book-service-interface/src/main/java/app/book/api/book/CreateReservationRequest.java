package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class CreateReservationRequest {
    @Property(name = "user_id")
    public Long userId;
}
