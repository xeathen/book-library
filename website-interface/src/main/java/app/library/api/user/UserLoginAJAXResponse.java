package app.library.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class UserLoginAJAXResponse {
    @Property(name = "user_id")
    public Long userId;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "error_code")
    public String errorCode;

    @Property(name = "error_message")
    public String errorMessage;
}
