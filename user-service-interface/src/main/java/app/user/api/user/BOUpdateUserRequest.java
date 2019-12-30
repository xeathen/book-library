package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOUpdateUserRequest {
    @Property(name = "password")
    public String password;

    @Property(name = "status")
    public UserStatusView status;
}
