package app.bo.user.web;

import app.bo.api.UserAJAXWebService;
import app.bo.api.user.ResetPasswordAJAXResponse;
import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.UpdateUserAJAXRequest;
import app.bo.api.user.UpdateUserAJAXResponse;
import app.bo.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService userService;

    @Override
    public CreateUserAJAXResponse create(CreateUserAJAXRequest request) {
        return userService.create(request);
    }

    @Override
    public DeleteUserAJAXResponse delete(Long id) {
        return userService.delete(id);
    }

    @Override
    public UpdateUserAJAXResponse update(Long id, UpdateUserAJAXRequest request) {
        return userService.update(id, request);
    }

    @Override
    public ResetPasswordAJAXResponse resetPassword(Long id) {
        return userService.resetPassword(id);
    }
}
