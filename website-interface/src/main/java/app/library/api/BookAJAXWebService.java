package app.library.api;

import app.library.api.book.BookAJAXView;
import app.library.api.book.SearchBookAJAXRequest;
import app.library.api.book.SearchBookAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BookAJAXWebService {
    @GET
    @Path("/ajax/book/:id")
    BookAJAXView get(@PathParam("id") Long id);

    @GET
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);
}
