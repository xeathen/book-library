package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class BOCreateCategoryRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String categoryName;
}
