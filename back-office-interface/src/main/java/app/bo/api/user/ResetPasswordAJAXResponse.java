package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class ResetPasswordAJAXResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;
}
