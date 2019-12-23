package app.user.api;

import app.user.api.user.GetUserResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserWebService {
    @GET
    @Path("/user/:id")
    GetUserResponse get(@PathParam("id") Long id);
}

