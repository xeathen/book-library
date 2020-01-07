package app.user.api;

import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface UserWebService {
    @PUT
    @Path("/user/login")
    UserLoginResponse login(UserLoginRequest request);
}

