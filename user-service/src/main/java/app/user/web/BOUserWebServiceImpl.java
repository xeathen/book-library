package app.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.BOChangeStatusRequest;
import app.user.api.user.BOChangeStatusResponse;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOListUserRequest;
import app.user.api.user.BOListUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserPasswordResponse;
import app.user.service.BOUserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BOUserWebServiceImpl implements BOUserWebService {
    @Inject
    BOUserService boUserService;

    @Override
    public BOGetUserResponse get(Long id) {
        return boUserService.get(id);
    }

    @Override
    public BOListUserResponse list(BOListUserRequest request) {
        return boUserService.list(request);
    }

    @Override
    public BOCreateUserResponse create(BOCreateUserRequest request) {
        ActionLogContext.put("username", request);
        return boUserService.create(request);
    }

    @Override
    public BOUpdateUserPasswordResponse updatePassword(Long id, BOUpdateUserPasswordRequest request) {
        ActionLogContext.put("userId", id);
        return boUserService.updatePassword(id, request);
    }

    @Override
    public BOResetPasswordResponse resetPassword(Long id) {
        ActionLogContext.put("userId", id);
        return boUserService.resetPassword(id);
    }

    @Override
    public BOChangeStatusResponse changeStatus(Long id, BOChangeStatusRequest request) {
        ActionLogContext.put("userId", id);
        return boUserService.changeStatus(id, request);
    }
}
