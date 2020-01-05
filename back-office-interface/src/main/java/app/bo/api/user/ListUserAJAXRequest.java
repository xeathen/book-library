package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author xeathen
 */
public class ListUserAJAXRequest {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit;
}
