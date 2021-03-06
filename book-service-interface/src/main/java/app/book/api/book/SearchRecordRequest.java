package app.book.api.book;

import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author Ethan
 */
public class SearchRecordRequest {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit;

    @NotNull
    @QueryParam(name = "userId")
    public Long userId;
}
