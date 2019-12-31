package app.web.api;

import app.web.api.user.GetUserAJAXResponse;
import app.web.api.user.UserLoginAJAXRequest;
import app.web.api.user.UserLoginAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserAJAXWebService {
    @GET
    @Path("/ajax/user/:id")
    GetUserAJAXResponse get(@PathParam("id") Long id);

    @POST
    @Path("/ajax/user/login")
    UserLoginAJAXResponse login(UserLoginAJAXRequest request);
}
