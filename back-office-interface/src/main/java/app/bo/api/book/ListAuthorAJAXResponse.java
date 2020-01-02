package app.bo.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class ListAuthorAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "authors")
    public List<AuthorView> authors;
}
