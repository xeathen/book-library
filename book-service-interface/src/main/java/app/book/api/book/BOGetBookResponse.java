package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOGetBookResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;

    @Property(name = "author_id")
    public Integer authorId;


    @Property(name = "category_id")
    public Integer categoryId;

    @Property(name = "tag_id")
    public Integer tagId;

    @Property(name = "pub")
    public String pub;

    @Property(name = "description")
    public String description;

    @Property(name = "num")
    public Integer num;
}
