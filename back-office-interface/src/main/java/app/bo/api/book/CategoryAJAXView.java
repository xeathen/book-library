package app.bo.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class CategoryAJAXView {
    @Property(name = "category_id")
    public Integer categoryId;

    @Property(name = "category_name")
    public String categoryName;
}
