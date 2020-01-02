package app.book.api.tag;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class BOListTagResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "tags")
    public List<TagView> tags;
}
