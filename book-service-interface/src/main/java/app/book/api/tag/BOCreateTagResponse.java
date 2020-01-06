package app.book.api.tag;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOCreateTagResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
