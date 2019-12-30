package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UpdateUserAJAXRequest {
    @Property(name = "user_name")
    public String userName;

    @Property(name = "user_email")
    public String userEmail;

    @Property(name = "password")
    public String password;

    @Property(name = "status")
    public UserStatusAJAXView status;
}
