package app.bo.api;

import app.bo.api.book.BookView;
import app.bo.api.book.CreateAuthorAJAXRequest;
import app.bo.api.book.CreateAuthorAJAXResponse;
import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.CreateCategoryAJAXRequest;
import app.bo.api.book.CreateCategoryAJAXResponse;
import app.bo.api.book.CreateTagAJAXRequest;
import app.bo.api.book.CreateTagAJAXResponse;
import app.bo.api.book.ListAuthorAJAXResponse;
import app.bo.api.book.ListCategoryAJAXResponse;
import app.bo.api.book.ListTagAJAXResponse;
import app.bo.api.book.SearchBookAJAXRequest;
import app.bo.api.book.SearchBookAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface BookAJAXWebService {
    @POST
    @Path("/ajax/book")
    CreateBookAJAXResponse create(CreateBookAJAXRequest request);

    @GET
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);

    @POST
    @Path("/ajax/book/category")
    CreateCategoryAJAXResponse createCategory(CreateCategoryAJAXRequest request);

    @POST
    @Path("/ajax/book/tag")
    CreateTagAJAXResponse createTag(CreateTagAJAXRequest request);

    @POST
    @Path("/ajax/book/author")
    CreateAuthorAJAXResponse createAuthor(CreateAuthorAJAXRequest request);

    @GET
    @Path("/ajax/book/category")
    ListCategoryAJAXResponse listCategory();

    @GET
    @Path("/ajax/book/tag")
    ListTagAJAXResponse listTag();

    @GET
    @Path("/ajax/book/author")
    ListAuthorAJAXResponse listAuthor();
}
