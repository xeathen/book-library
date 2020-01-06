package app.bo.api.category;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class CreateCategoryAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;
}
