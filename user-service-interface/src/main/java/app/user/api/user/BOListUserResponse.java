package app.user.api.user;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class BOListUserResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "users")
    public List<UserView> users;
}
