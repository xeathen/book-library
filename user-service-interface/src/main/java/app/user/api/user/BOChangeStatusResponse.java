package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOChangeStatusResponse {
    @Property(name = "status")
    public UserStatusView status;
}
