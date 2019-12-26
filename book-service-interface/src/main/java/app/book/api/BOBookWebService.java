package app.book.api;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetAuthorResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOGetCategoryResponse;
import app.book.api.book.BOGetTagResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordByBookIdResponse;
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
    BOGetBookResponse get(@PathParam("id") Long id);

    @GET
    @Path("/bo/book")
    BOSearchBookResponse search(BOSearchBookRequest request);

    @GET
    @Path("/bo/book/category")
    BOGetCategoryResponse getCategories();

    @GET
    @Path("/bo/book/tag")
    BOGetTagResponse getTags();

    @GET
    @Path("/bo/book/author")
    BOGetAuthorResponse getAuthors();

    @POST
    @Path("/bo/book")
    BOCreateBookResponse create(BOCreateBookRequest request);

    @POST
    @Path("/bo/book/category")
    BOCreateBookResponse createInCategory(BOCreateBookRequest request);

    @POST
    @Path("/bo/book/tag")
    BOCreateBookResponse createInTag(BOCreateBookRequest request);

    @POST
    @Path("/bo/book/author")
    BOCreateBookResponse createInAuthor(BOCreateBookRequest request);

    @PUT
    @Path("/bo/book/:id")
    BOUpdateBookResponse update(@PathParam("id") Long id, BOUpdateBookRequest request);

    @GET
    @Path("/bo/borrowed-record/:id")
    BOSearchRecordByBookIdResponse searchRecordByBookId(@PathParam("id") Long bookId);
}
