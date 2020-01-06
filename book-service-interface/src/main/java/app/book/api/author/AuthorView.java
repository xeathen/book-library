package app.book.api.author;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class AuthorView {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
