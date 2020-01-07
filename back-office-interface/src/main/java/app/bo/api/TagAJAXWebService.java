package app.bo.api;

import app.bo.api.tag.CreateTagAJAXRequest;
import app.bo.api.tag.CreateTagAJAXResponse;
import app.bo.api.tag.ListTagAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface TagAJAXWebService {
    @POST
    @Path("/ajax/tag")
    CreateTagAJAXResponse create(CreateTagAJAXRequest request);

    @GET
    @Path("/ajax/book/tag")
    ListTagAJAXResponse list();
}
