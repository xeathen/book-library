package app.bo.user.web;

import app.bo.api.UserAJAXWebService;
import app.bo.api.user.ChangeStatusAJAXRequest;
import app.bo.api.user.ChangeStatusAJAXResponse;
import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.GetUserAJAXResponse;
import app.bo.api.user.ListUserAJAXRequest;
import app.bo.api.user.ListUserAJAXResponse;
import app.bo.api.user.ResetPasswordAJAXResponse;
import app.bo.api.user.UpdateUserPasswordAJAXRequest;
import app.bo.api.user.UpdateUserPasswordAJAXResponse;
import app.bo.user.service.UserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

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

    @Override
    public ListUserAJAXResponse list(ListUserAJAXRequest request) {
        return userService.list(request);
    }

    @Override
    public CreateUserAJAXResponse create(CreateUserAJAXRequest request) {
        ActionLogContext.put("username", request.username);
        ActionLogContext.put("userEmail", request.email);
        return userService.create(request);
    }

    @Override
    public UpdateUserPasswordAJAXResponse updatePassword(Long userId, UpdateUserPasswordAJAXRequest request) {
        ActionLogContext.put("userId", userId);
        return userService.updatePassword(userId, request);
    }

    @Override
    public ResetPasswordAJAXResponse resetPassword(Long userId) {
        ActionLogContext.put("userId", userId);
        return userService.resetPassword(userId);
    }

    @Override
    public ChangeStatusAJAXResponse changeStatus(Long userId, ChangeStatusAJAXRequest request) {
        ActionLogContext.put("userId", userId);
        return userService.changeStatus(userId, request);
    }
}
