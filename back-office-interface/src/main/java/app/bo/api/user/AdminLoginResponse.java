package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class AdminLoginResponse {
    @Property(name = "id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;
}
