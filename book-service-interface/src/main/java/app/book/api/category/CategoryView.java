package app.book.api.category;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class CategoryView {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
