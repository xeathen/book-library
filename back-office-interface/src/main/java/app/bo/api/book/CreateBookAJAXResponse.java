package app.bo.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class CreateBookAJAXResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;
}
