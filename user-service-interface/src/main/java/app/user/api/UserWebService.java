package app.user.api;

import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface UserWebService {
    @POST
    @Path("/user/login")
    UserLoginResponse login(UserLoginRequest request);
}

