package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class ChangeStatusAJAXRequest {
    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
