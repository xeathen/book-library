package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UpdateUserPasswordAJAXRequest {
    @Property(name = "password")
    public String password;
}
