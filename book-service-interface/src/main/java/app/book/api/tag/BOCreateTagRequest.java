package app.book.api.tag;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class BOCreateTagRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;
}
