package app.bo.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Min;

/**
 * @author Ethan
 */
public class UpdateBookAJAXRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "author_id")
    public Integer authorId;

    @Property(name = "category_id")
    public Integer categoryId;

    @Property(name = "tag_id")
    public Integer tagId;

    @Property(name = "publishing_house")
    public String publishingHouse;

    @Property(name = "description")
    public String description;

    @Min(0)
    @Property(name = "quantity")
    public Integer quantity;
}
