package app.library.api.book;

import core.framework.api.json.Property;
import core.framework.util.Strings;

/**
 * @author Ethan
 */
public class GetBookAJAXResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;

    @Property(name = "author_id")
    public Integer authorId;

    @Property(name = "category_id")
    public Integer categoryId;

    @Property(name = "tag")
    public Integer tagId;

    @Property(name = "pub")
    public String pub;

    @Property(name = "description")
    public String description;

    @Property(name = "num")
    public Integer num;
}
