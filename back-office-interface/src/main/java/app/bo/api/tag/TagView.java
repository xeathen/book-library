package app.bo.api.tag;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class TagView {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
