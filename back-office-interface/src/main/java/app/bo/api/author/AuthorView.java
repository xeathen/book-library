package app.bo.api.author;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class AuthorView {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
