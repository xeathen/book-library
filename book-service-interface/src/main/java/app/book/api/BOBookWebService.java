package app.book.api;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BOBookWebService {
    @POST
    @Path("/bo/book")
    BOCreateBookResponse create(BOCreateBookRequest request);

    @PUT
    @Path("/bo/book/:id")
    BOUpdateBookResponse update(@PathParam("id") Long id, BOUpdateBookRequest request);
}
