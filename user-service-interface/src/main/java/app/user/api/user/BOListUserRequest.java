package app.user.api.user;

import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author Ethan
 */
public class BOListUserRequest {
    @NotNull
    @QueryParam(name = "skip")
    public Integer skip;

    @NotNull
    @QueryParam(name = "limit")
    public Integer limit;
}
