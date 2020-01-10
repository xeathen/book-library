package app.bo.api.tag;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class ListTagAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "tags")
    public List<Tag> tags;

    public static class Tag {
        @Property(name = "id")
        public Integer id;

        @Property(name = "name")
        public String name;
    }
}
