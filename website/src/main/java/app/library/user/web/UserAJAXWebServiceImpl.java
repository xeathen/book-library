package app.library.user.web;

import app.library.api.UserAJAXWebService;
import app.library.api.user.GetUserAJAXResponse;
import app.library.api.user.UserLoginAJAXRequest;
import app.library.api.user.UserLoginAJAXResponse;
import app.library.user.service.UserService;
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
        ActionLogContext.put("user_id", userId);
        return userService.get(userId);
    }

    @Override
    public UserLoginAJAXResponse login(UserLoginAJAXRequest request) {
        ActionLogContext.put("user_name", request.userName);

        return userService.login(request);
    }
}
