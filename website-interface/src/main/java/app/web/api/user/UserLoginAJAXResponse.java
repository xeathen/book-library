package app.web.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UserLoginAJAXResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "login_message")
    public LoginMessage loginMessage;
}
