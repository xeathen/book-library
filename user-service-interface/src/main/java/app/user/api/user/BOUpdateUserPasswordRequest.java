package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class BOUpdateUserPasswordRequest {
    @Property(name = "password")
    public String password;
}
