package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class GetUserResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "username")
    public String username;

    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusView status;
}
