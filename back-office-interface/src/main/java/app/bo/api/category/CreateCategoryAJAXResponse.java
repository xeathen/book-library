package app.bo.api.category;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class CreateCategoryAJAXResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String categoryName;
}