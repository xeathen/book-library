package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOChangeStatusResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "status")
    public UserStatusView status;
}
