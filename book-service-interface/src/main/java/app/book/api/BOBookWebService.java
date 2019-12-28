package app.book.api;

import app.book.api.book.BOCreateAuthorRequest;
import app.book.api.book.BOCreateAuthorResponse;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOCreateCategoryRequest;
import app.book.api.book.BOCreateCategoryResponse;
import app.book.api.book.BOCreateTagRequest;
import app.book.api.book.BOCreateTagResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOListAuthorResponse;
import app.book.api.book.BOListCategoryResponse;
import app.book.api.book.BOListTagResponse;
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
    BOListCategoryResponse listCategory();

    @GET
    @Path("/bo/book/tag")
    BOListTagResponse listTag();

    @GET
    @Path("/bo/book/author")
    BOListAuthorResponse listAuthor();

    @POST
    @Path("/bo/book")
    BOCreateBookResponse create(BOCreateBookRequest request);

    @POST
    @Path("/bo/book/category")
    BOCreateCategoryResponse createCategory(BOCreateCategoryRequest request);

    @POST
    @Path("/bo/book/tag")
    BOCreateTagResponse createTag(BOCreateTagRequest request);

    @POST
    @Path("/bo/book/author")
    BOCreateAuthorResponse createAuthor(BOCreateAuthorRequest request);

    @PUT
    @Path("/bo/book/:id")
    BOUpdateBookResponse update(@PathParam("id") Long id, BOUpdateBookRequest request);

    @GET
    @Path("/bo/borrowed-record/:id")
    BOSearchRecordByBookIdResponse searchRecordByBookId(@PathParam("id") Long bookId);
}
