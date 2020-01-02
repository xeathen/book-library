package app.book.api;

import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOCreateCategoryResponse;
import app.book.api.category.BOListCategoryResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface BOCategoryWebService {
    @GET
    @Path("/bo/book/category")
    BOListCategoryResponse list();

    @POST
    @Path("/bo/book/category")
    BOCreateCategoryResponse create(BOCreateCategoryRequest request);
}
