package app.bo.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class ListTagAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "tags")
    public List<TagView> tags;
}
