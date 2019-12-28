package app.bo.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class AdminLoginResponse {
    @Property(name = "id")
    public String userId;

    @Property(name = "user_name")
    public String userName;
}
