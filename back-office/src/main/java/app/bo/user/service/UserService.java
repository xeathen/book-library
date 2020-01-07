package app.bo.user.service;

import app.bo.api.user.ChangeStatusAJAXResponse;
import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.GetUserAJAXResponse;
import app.bo.api.user.ListUserAJAXRequest;
import app.bo.api.user.ListUserAJAXResponse;
import app.bo.api.user.ListUserAJAXResponse.User;
import app.bo.api.user.ResetPasswordAJAXResponse;
import app.bo.api.user.UpdateUserPasswordAJAXRequest;
import app.bo.api.user.UpdateUserPasswordAJAXResponse;
import app.bo.api.user.UserStatusAJAXView;
import app.user.api.BOUserWebService;
import app.user.api.user.BOChangeStatusResponse;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOListUserRequest;
import app.user.api.user.BOListUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserPasswordResponse;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    BOUserWebService boUserWebService;

    public GetUserAJAXResponse get(Long id) {
        return getUserAJAXResponse(boUserWebService.get(id));
    }

    public ListUserAJAXResponse list(ListUserAJAXRequest ajaxRequest) {
        BOListUserRequest request = bolistUserRequest(ajaxRequest);
        return listUserAJAXResponse(boUserWebService.list(request));
    }

    public CreateUserAJAXResponse create(CreateUserAJAXRequest ajaxRequest) {
        BOCreateUserRequest boRequest = boCreateUserRequest(ajaxRequest);
        return createUserAJAXResponse(boUserWebService.create(boRequest));
    }

    public DeleteUserAJAXResponse delete(Long id) {
        DeleteUserAJAXResponse response = new DeleteUserAJAXResponse();
        response.id = boUserWebService.delete(id).id;
        return response;
    }

    public UpdateUserPasswordAJAXResponse updatePassword(Long id, UpdateUserPasswordAJAXRequest ajaxRequest) {
        BOUpdateUserPasswordRequest request = boUpdateUserRequest(ajaxRequest);
        return updateUserAJAXResponse(boUserWebService.updatePassword(id, request));
    }

    public ResetPasswordAJAXResponse resetPassword(Long id) {
        return resetPasswordAJAXResponse(boUserWebService.resetPassword(id));
    }

    public ChangeStatusAJAXResponse changeStatus(Long id) {
        return changeStatusAJAXResponse(boUserWebService.changeStatus(id));
    }

    private GetUserAJAXResponse getUserAJAXResponse(BOGetUserResponse response) {
        GetUserAJAXResponse ajaxResponse = new GetUserAJAXResponse();
        ajaxResponse.id = response.id;
        ajaxResponse.userName = response.userName;
        ajaxResponse.email = response.email;
        ajaxResponse.status = response.status == null ? null : UserStatusAJAXView.valueOf(response.status.name());
        return ajaxResponse;
    }

    private BOListUserRequest bolistUserRequest(ListUserAJAXRequest ajaxRequest) {
        BOListUserRequest request = new BOListUserRequest();
        request.skip = ajaxRequest.skip;
        request.limit = ajaxRequest.limit;
        return request;
    }

    private ListUserAJAXResponse listUserAJAXResponse(BOListUserResponse response) {
        ListUserAJAXResponse ajaxResponse = new ListUserAJAXResponse();
        ajaxResponse.users = response.users.stream().map(userView -> {
            User user = new User();
            user.id = userView.id;
            user.userName = userView.userName;
            user.email = userView.email;
            user.status = userView.status == null ? null : UserStatusAJAXView.valueOf(userView.status.name());
            return user;
        }).collect(Collectors.toList());
        ajaxResponse.total = response.total;
        return ajaxResponse;
    }

    private CreateUserAJAXResponse createUserAJAXResponse(BOCreateUserResponse boResponse) {
        CreateUserAJAXResponse ajaxResponse = new CreateUserAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.userName = boResponse.userName;
        ajaxResponse.email = boResponse.email;
        ajaxResponse.status = boResponse.status == null ? null : UserStatusAJAXView.valueOf(boResponse.status.name());
        return ajaxResponse;
    }

    private BOCreateUserRequest boCreateUserRequest(CreateUserAJAXRequest ajaxRequest) {
        BOCreateUserRequest boRequest = new BOCreateUserRequest();
        boRequest.userName = ajaxRequest.userName;
        boRequest.password = ajaxRequest.password;
        boRequest.email = ajaxRequest.email;
        boRequest.status = ajaxRequest.status == null ? null : UserStatusView.valueOf(ajaxRequest.status.name());
        return boRequest;
    }

    private BOUpdateUserPasswordRequest boUpdateUserRequest(UpdateUserPasswordAJAXRequest ajaxRequest) {
        BOUpdateUserPasswordRequest boRequest = new BOUpdateUserPasswordRequest();
        boRequest.password = ajaxRequest.password;
        return boRequest;
    }

    private UpdateUserPasswordAJAXResponse updateUserAJAXResponse(BOUpdateUserPasswordResponse boResponse) {
        UpdateUserPasswordAJAXResponse ajaxResponse = new UpdateUserPasswordAJAXResponse();
        ajaxResponse.id = boResponse.id;
        return ajaxResponse;
    }

    private ResetPasswordAJAXResponse resetPasswordAJAXResponse(BOResetPasswordResponse boResponse) {
        ResetPasswordAJAXResponse response = new ResetPasswordAJAXResponse();
        response.userId = boResponse.userId;
        response.userName = boResponse.userName;
        return response;
    }

    private ChangeStatusAJAXResponse changeStatusAJAXResponse(BOChangeStatusResponse boResponse) {
        ChangeStatusAJAXResponse response = new ChangeStatusAJAXResponse();
        response.userId = boResponse.userId;
        response.userName = boResponse.userName;
        response.status = boResponse.status == null ? null : UserStatusAJAXView.valueOf(boResponse.status.name());
        return response;
    }
}
