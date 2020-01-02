package app.bo.api.administrator;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class AdminLoginResponse {
    @Property(name = "id")
    public Long adminId;

    @Property(name = "admin_name")
    public String adminName;

    @Property(name = "login_message")
    public LoginMessage loginMessage;
}
