package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class UpdateUserPasswordAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
