package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class BOChangeStatusRequest {
    @Property(name = "status")
    public UserStatusView status;
}
