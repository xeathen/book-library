package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOCreateBookResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;
}
