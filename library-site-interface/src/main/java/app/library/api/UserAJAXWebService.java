package app.library.api;

import app.library.api.user.GetUserAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserAJAXWebService {
    @GET
    @Path("/ajax/user/:id")
    GetUserAJAXResponse get(@PathParam("id") Long id);
}
