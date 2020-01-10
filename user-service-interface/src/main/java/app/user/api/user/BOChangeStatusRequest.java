package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class BOChangeStatusRequest {
    @NotNull
    @Property(name = "status")
    public UserStatusView status;
}
