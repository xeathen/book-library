package app.book.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class BOGetCategoryResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "categories")
    public List<String> categories;
}
