package app.bo.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class SearchBookAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "books")
    public List<BookAJAXView> books;
}
