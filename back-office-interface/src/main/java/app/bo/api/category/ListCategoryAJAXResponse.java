package app.bo.api.category;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class ListCategoryAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "categories")
    public List<Category> categories;

    public static class Category {
        @Property(name = "id")
        public Integer id;

        @Property(name = "name")
        public String name;
    }
}
