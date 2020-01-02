package app.book.api.tag;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class BOCreateTagRequest {
    @NotNull
    @NotBlank
    @Property(name = "tag_name")
    public String tagName;
}
