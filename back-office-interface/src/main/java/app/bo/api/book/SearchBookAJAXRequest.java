package app.bo.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author Ethan
 */
public class SearchBookAJAXRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    public Integer limit;

    @Property(name = "name")
    public String name;

    @Property(name = "author")
    @Length(max = 50)
    public String author;

    @Property(name = "publishing_house")
    public String publishingHouse;

    @Property(name = "category")
    public String category;

    @Property(name = "tag")
    public String tag;

    @Property(name = "description")
    public String description;
}
