package app.web.api;

import app.web.api.book.BookAJAXView;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
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
