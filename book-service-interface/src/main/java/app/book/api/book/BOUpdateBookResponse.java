package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOUpdateBookResponse {
    @Property(name= "id")
    public Long id;

    @Property(name = "name")
    public String name;

    @Property(name = "author")
    public String author;

    @Property(name = "pub")
    public String pub;

    @Property(name = "category")
    public String category;

    @Property(name = "tag")
    public String tag;

    @Property(name = "description")
    public String description;

    @Property(name = "num")
    public Integer num;
}
