package app.bo.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class BookView {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;

    //TODO:name
    @Property(name = "author_name")
    public String authorName;

    @Property(name = "category_name")
    public String categoryName;

    @Property(name = "tag_id")
    public String tagName;

    @Property(name = "pub")
    public String pub;

    @Property(name = "description")
    public String description;

    @Property(name = "num")
    public Integer num;
}
