package app.bo.user.web;

import app.bo.api.UserAJAXWebService;
import app.bo.api.user.ChangeStatusAJAXResponse;
import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.ListUserAJAXResponse;
import app.bo.api.user.ResetPasswordAJAXResponse;
import app.bo.api.user.UpdateUserAJAXRequest;
import app.bo.api.user.UpdateUserAJAXResponse;
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
    public ListUserAJAXResponse listUser() {
        return userService.list();
    }

    @Override
    public CreateUserAJAXResponse create(CreateUserAJAXRequest request) {
        ActionLogContext.put("user_name", request.userName);
        ActionLogContext.put("user_email", request.userEmail);
        return userService.create(request);
    }

    @Override
    public DeleteUserAJAXResponse delete(Long userId) {
        ActionLogContext.put("user_id", userId);

        return userService.delete(userId);
    }

    @Override
    public UpdateUserAJAXResponse update(Long userId, UpdateUserAJAXRequest request) {
        ActionLogContext.put("user_id", userId);
        return userService.update(userId, request);
    }

    @Override
    public ResetPasswordAJAXResponse resetPassword(Long userId) {
        ActionLogContext.put("user_id", userId);
        return userService.resetPassword(userId);
    }

    @Override
    public ChangeStatusAJAXResponse changeStatus(Long userId) {
        ActionLogContext.put("user_id", userId);
        return userService.changeStatus(userId);
    }
}
