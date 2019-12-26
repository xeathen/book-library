package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class GetUserResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "user_email")
    public String userEmail;

    @Property(name = "status")
    public Boolean status;
}
