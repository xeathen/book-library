package app.bo.api.administrator;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public enum LoginMessage {
    @Property(name = "successful")
    SUCCESSFUL,
    @Property(name = "administrator_not_found")
    ADMINISTRATOR_NOT_FOUND,
    @Property(name = "wrong_password")
    WRONG_PASSWORD,
    @Property(name = "already_login")
    ALREADY_LOGIN
}
