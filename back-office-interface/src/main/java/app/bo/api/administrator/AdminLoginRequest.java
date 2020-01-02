package app.bo.api.administrator;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class AdminLoginRequest {
    @NotNull
    @NotBlank
    @Property(name = "admin_name")
    public String adminName;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
