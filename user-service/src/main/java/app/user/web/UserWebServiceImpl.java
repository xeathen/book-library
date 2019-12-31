package app.user.web;

import app.user.api.UserWebService;
import app.user.api.user.GetUserResponse;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.service.UserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class UserWebServiceImpl implements UserWebService {
    @Inject
    UserService userService;

    @Override
    public GetUserResponse get(Long id) {
        ActionLogContext.put("user_id", id);
        return userService.get(id);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        ActionLogContext.put("user_name", request.userName);
        return userService.login(request);
    }
}
