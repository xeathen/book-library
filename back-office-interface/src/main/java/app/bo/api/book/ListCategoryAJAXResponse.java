package app.bo.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class ListCategoryAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "categories")
    public List<CategoryAJAXView> categories;
}
