package app.book.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class SearchBookResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "books")
    public List<BookView> books;
}
