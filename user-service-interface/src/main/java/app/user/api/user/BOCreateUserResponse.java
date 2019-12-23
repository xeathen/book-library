package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOCreateUserResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "password")
    public String password;

    @Property(name = "user_email")
    public String userEmail;
}
