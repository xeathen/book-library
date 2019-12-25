package app.book.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class BOGetAuthorResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "authors")
    public List<String> authors;
}
