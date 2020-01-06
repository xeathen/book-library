package app.bo.api.category;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class CreateCategoryAJAXResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
