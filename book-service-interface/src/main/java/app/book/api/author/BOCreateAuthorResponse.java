package app.book.api.author;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class BOCreateAuthorResponse {
    @Property(name = "id")
    public Integer id;

    @Property(name = "author_name")
    public String authorName;
}