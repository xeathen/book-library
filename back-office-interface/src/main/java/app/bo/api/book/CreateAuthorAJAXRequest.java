package app.bo.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author xeathen
 */
public class CreateAuthorAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String authorName;
}
