package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOUpdateUserRequest {
    @Property(name = "user_name")
    public String userName;

    //TODO:update-password / 密码要加密
    @Property(name = "password")
    public String password;

    @Property(name = "email")
    public String email;
}
