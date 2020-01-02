package app.web.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class CategoryView {
    @Property(name = "category_id")
    public Integer categoryId;

    @Property(name = "category_name")
    public String categoryName;
}
