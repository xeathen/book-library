package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class BOCreateTagResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "tag_name")
    public String tagName;
}
