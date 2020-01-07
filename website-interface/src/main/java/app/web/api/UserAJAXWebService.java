package app.web.api;

import app.web.api.user.UserLoginAJAXRequest;
import app.web.api.user.UserLoginAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface UserAJAXWebService {
    @PUT
    @Path("/ajax/user/login")
    UserLoginAJAXResponse login(UserLoginAJAXRequest request);
}
