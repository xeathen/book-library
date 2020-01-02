package app.web.user.web;

import app.web.api.UserAJAXWebService;
import app.web.api.user.GetUserAJAXResponse;
import app.web.api.user.UserLoginAJAXRequest;
import app.web.api.user.UserLoginAJAXResponse;
import app.web.user.service.UserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService userService;

    @Override
    public GetUserAJAXResponse get(Long userId) {
        ActionLogContext.put("userId", userId);
        return userService.get(userId);
    }

    @Override
    public UserLoginAJAXResponse login(UserLoginAJAXRequest request) {
        //TODO:email
        ActionLogContext.put("userName", request.userName);
        return userService.login(request);
    }
}