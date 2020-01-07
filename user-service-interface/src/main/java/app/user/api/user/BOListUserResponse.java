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
    public List<User> users;

    public static class User {
        @Property(name = "id")
        public Long id;

        @Property(name = "user_name")
        public String userName;

        @Property(name = "email")
        public String email;

        @Property(name = "status")
        public UserStatusView status;
    }
}
