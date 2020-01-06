package app.book.api.category;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class BOCreateCategoryRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;
}
