package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class BOCreateAuthorRequest {
    @NotNull
    @NotBlank
    @Property(name = "author_name")
    public String authorName;
}
