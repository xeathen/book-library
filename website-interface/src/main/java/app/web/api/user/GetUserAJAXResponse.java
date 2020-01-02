package app.web.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class GetUserAJAXResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusAJAXView status;
}
