package app.bo.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class TagView {
    @Property(name = "tag_id")
    public Integer tagId;

    @Property(name = "tag_name")
    public String tagName;
}
