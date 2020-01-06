package app.web.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class ReturnBackBookAJAXRequest {
    @NotNull
    @Property(name = "book_id")
    public Long bookId;
}
