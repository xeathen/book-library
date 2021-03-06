package app.web.api.book;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class GetBookAJAXResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;

    @Property(name = "author_name")
    public String authorName;

    @Property(name = "category_name")
    public String categoryName;

    @Property(name = "tag_id")
    public String tagName;

    @Property(name = "publishing_house")
    public String publishingHouse;

    @Property(name = "description")
    public String description;

    @Property(name = "quantity")
    public Integer quantity;
}
