package app.library.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class SearchBookAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "books")
    public List<GetBookAJAXResponse> books;
}
