package app.bo.api.administrator;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class AdminLoginResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;

    @Property(name = "login_message")
    public LoginMessage loginMessage;
}
