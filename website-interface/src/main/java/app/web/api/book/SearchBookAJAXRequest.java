package app.web.api.book;

import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author Ethan
 */
public class SearchBookAJAXRequest {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit;

    @QueryParam(name = "name")
    public String name;

    @QueryParam(name = "category")
    public String category;

    @QueryParam(name = "tag")
    public String tag;

    @QueryParam(name = "author")
    public String author;

    @QueryParam(name = "publishing_house")
    public String publishingHouse;

    @QueryParam(name = "description")
    public String description;
}
