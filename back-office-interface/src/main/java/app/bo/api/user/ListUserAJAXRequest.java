package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class ListUserAJAXRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    public Integer limit;
}
