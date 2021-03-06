package app.bo.api.author;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author Ethan
 */
public class CreateAuthorAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;
}
