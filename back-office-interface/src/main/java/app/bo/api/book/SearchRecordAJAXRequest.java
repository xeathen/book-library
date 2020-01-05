package app.bo.api.book;

import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author xeathen
 */
public class SearchRecordAJAXRequest {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit;

    @NotNull
    @QueryParam(name = "bookId")
    public Long bookId;
}
