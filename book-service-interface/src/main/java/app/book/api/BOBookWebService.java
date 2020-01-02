package app.book.api;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BOBookWebService {
    @GET
    @Path("/bo/book/:id")
    BOGetBookResponse get(@PathParam("id") Long bookId);

    @GET
    @Path("/bo/book")
    BOSearchBookResponse search(BOSearchBookRequest request);

    @POST
    @Path("/bo/book")
    BOCreateBookResponse create(BOCreateBookRequest request);

    @PUT
    @Path("/bo/book/:id")
    BOUpdateBookResponse update(@PathParam("id") Long bookId, BOUpdateBookRequest request);

    @GET
    @Path("/bo/borrowed-record/:id")
    BOSearchRecordResponse searchRecordByBookId(@PathParam("id") Long bookId);
}
