package app.bo.api.tag;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class TagView {
    @Property(name = "id")
    public Integer id;

    @Property(name = "name")
    public String name;
}
