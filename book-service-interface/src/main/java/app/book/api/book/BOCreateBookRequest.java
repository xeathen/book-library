package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class BOCreateBookRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @Property(name = "author")
    public String author;

    @NotNull
    @NotBlank
    @Property(name = "pub")
    public String pub;

    @Property(name = "category")
    public String category;

    @Property(name = "tag")
    public String tag;

    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "num")
    public Integer num;
}
