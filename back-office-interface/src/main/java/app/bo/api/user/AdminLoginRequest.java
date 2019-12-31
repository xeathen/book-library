package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class AdminLoginRequest {
    @NotNull
    @NotBlank
    @Property(name = "user_name")
    public String userName;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
