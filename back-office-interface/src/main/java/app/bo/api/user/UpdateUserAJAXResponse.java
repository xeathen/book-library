package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UpdateUserAJAXResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "user_email")
    public String userEmail;
}
