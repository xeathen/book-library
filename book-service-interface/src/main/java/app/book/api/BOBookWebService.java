package app.book.api;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchHistoryResponse;
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
    @POST
    @Path("/bo/book")
    BOCreateBookResponse create(BOCreateBookRequest request);

//    @POST
//    @Path("/bo/book/category")
//    BOCreateInCategoryResponse create()
//
//    @POST
//    @Path("/bo/book/tag")
//
//    @POST
//    @Path("/bo/book/author")

    @PUT
    @Path("/bo/book/:id")
    BOUpdateBookResponse update(@PathParam("id") Long id, BOUpdateBookRequest request);

    @GET
    @Path("/bo/book/:id")
    BOSearchHistoryResponse getBorrowedHistory(@PathParam("id") Long id);

    @GET
    @Path("/bo/book")
    BOSearchBookResponse search(BOSearchBookRequest request);
}
