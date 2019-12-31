package app.bo.api.user;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class ListUserAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "users")
    public List<UserAJAXView> users;
}
