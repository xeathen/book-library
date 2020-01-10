package app.bo.api.administrator;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public enum LoginMessage {
    @Property(name = "SUCCESSFUL")
    SUCCESSFUL,
    @Property(name = "ADMINISTRATOR_NOT_FOUND")
    ADMINISTRATOR_NOT_FOUND,
    @Property(name = "wrong_password")
    WRONG_PASSWORD,
    @Property(name = "already_login")
    ALREADY_LOGIN
}
