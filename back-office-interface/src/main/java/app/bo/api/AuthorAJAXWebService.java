package app.bo.api;

import app.bo.api.author.CreateAuthorAJAXRequest;
import app.bo.api.author.CreateAuthorAJAXResponse;
import app.bo.api.author.ListAuthorAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface AuthorAJAXWebService {
    @POST
    @Path("/ajax/book/author")
    CreateAuthorAJAXResponse create(CreateAuthorAJAXRequest request);

    @GET
    @Path("/ajax/book/author")
    ListAuthorAJAXResponse list();
}
