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
    @Property(name = "user_name")
    public String userName;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "user_email")
    public String userEmail;

    @NotNull
    @Property(name = "status")
    public Boolean status;
}
