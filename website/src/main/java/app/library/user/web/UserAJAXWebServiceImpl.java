package app.library.user.web;

import app.library.api.UserAJAXWebService;
import app.library.api.user.GetUserAJAXResponse;
import app.library.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService userService;

    @Override
    public GetUserAJAXResponse get(Long id) {
        return userService.get(id);
    }
}
