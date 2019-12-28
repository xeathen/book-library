package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class BOCreateCategoryResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String categoryName;
}
