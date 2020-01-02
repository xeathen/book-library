package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public enum LoginMessage {
    @Property(name = "successful")
    SUCCESSFUL,
    @Property(name = "user_not_found")
    USER_NOT_FOUND,
    @Property(name = "wrong_password")
    WRONG_PASSWORD,
    @Property(name = "already_login")
    ALREADY_LOGIN
}
