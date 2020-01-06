package app.book.api.category;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class CategoryView {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
