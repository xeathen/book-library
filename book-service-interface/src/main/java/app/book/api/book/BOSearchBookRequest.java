package app.book.api.book;

import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author Ethan
 */
public class BOSearchBookRequest {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip = 0;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit = 1000;

    @QueryParam(name = "name")
    public String name;

    @QueryParam(name = "author")
    public String author;

    @QueryParam(name = "pub")
    public String pub;

    @QueryParam(name = "category")
    public String category;

    @QueryParam(name = "tag")
    public String tag;

    @QueryParam(name = "description")
    public String description;
}
