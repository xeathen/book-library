package app.bo.api;

import app.bo.api.category.CreateCategoryAJAXRequest;
import app.bo.api.category.CreateCategoryAJAXResponse;
import app.bo.api.category.ListCategoryAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface CategoryAJAXWebService {
    @POST
    @Path("/ajax/category")
    CreateCategoryAJAXResponse create(CreateCategoryAJAXRequest request);

    @GET
    @Path("/ajax/category")
    ListCategoryAJAXResponse list();
}
