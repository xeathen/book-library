package app.bo.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class CreateBookAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "author_id")
    public Integer authorId;

    @NotNull
    @Property(name = "category_id")
    public Integer categoryId;

    @NotNull
    @Property(name = "tag_id")
    public Integer tagId;

    @NotNull
    @NotBlank
    @Property(name = "publishing_house")
    public String publishingHouse;

    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "amount")
    public Integer amount;
}
