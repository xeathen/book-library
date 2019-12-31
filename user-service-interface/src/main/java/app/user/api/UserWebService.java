package app.user.api;

import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserView;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserWebService {
    @GET
    @Path("/user/:id")
    UserView get(@PathParam("id") Long id);

    @POST
    @Path("/user/login")
    UserLoginResponse login(UserLoginRequest request);
}

