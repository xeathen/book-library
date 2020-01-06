package app.book.api.category;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOCreateCategoryResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
