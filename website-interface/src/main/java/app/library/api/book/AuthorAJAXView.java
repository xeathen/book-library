package app.library.api.book;

import core.framework.api.json.Property;

/**
 * @author xeathen
 */
public class AuthorAJAXView {
    @Property(name = "author_id")
    public Integer authorId;

    @Property(name = "author_name")
    public String authorName;
}
