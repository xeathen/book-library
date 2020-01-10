package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class CreateUserAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "username")
    public String username;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "email")
    public String email;

    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
