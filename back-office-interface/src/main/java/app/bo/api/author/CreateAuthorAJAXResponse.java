package app.bo.api.author;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class CreateAuthorAJAXResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
