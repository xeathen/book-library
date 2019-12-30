package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UpdateUserAJAXRequest {
    @Property(name = "password")
    public String password;

    @Property(name = "status")
    public UserStatusAJAXView status;
}
