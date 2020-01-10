package app.user.api;

import app.user.api.user.GetUserResponse;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserWebService {
    @GET
    @Path("/user/:id")
    GetUserResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/user/login")
    UserLoginResponse login(UserLoginRequest request);
}

