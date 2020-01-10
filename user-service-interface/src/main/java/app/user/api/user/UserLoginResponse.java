package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UserLoginResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "username")
    public String username;

    @Property(name = "login_message")
    public LoginMessage loginMessage;
}
