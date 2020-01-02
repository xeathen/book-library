package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UpdateUserAJAXRequest {
    @Property(name = "user_name")
    public String userName;

    @Property(name = "email")
    public String email;
}
