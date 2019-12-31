package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UserLoginResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;
}
