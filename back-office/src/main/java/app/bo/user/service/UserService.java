package app.bo.user.service;

import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.UpdateUserAJAXRequest;
import app.bo.api.user.UpdateUserAJAXResponse;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import core.framework.api.web.service.PathParam;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    BOUserWebService boUserWebService;

    public CreateUserAJAXResponse create(CreateUserAJAXRequest request) {
        CreateUserAJAXResponse response = new CreateUserAJAXResponse();
        BOCreateUserRequest boRequest = new BOCreateUserRequest();
        convert(request, boRequest);
        convert(boUserWebService.create(boRequest), response);
        return response;
    }

    public DeleteUserAJAXResponse delete(Long id) {
        DeleteUserAJAXResponse response = new DeleteUserAJAXResponse();
        boUserWebService.delete(id);
        response.id = id;
        return response;
    }

    public UpdateUserAJAXResponse update(Long id, UpdateUserAJAXRequest request) {
        UpdateUserAJAXResponse response = new UpdateUserAJAXResponse();
        BOUpdateUserRequest boRequest = new BOUpdateUserRequest();
        convert(request, boRequest);
        convert(boUserWebService.update(id, boRequest), response);
        return response;
    }

    private void convert(CreateUserAJAXRequest ajaxRequest, BOCreateUserRequest boRequest) {
        boRequest.userName = ajaxRequest.userName;
        boRequest.password = ajaxRequest.password;
        boRequest.userEmail = ajaxRequest.userEmail;
        boRequest.status = ajaxRequest.status;
    }

    private void convert(BOCreateUserResponse boResponse, CreateUserAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.userName = boResponse.userName;
        ajaxResponse.password = boResponse.password;
        ajaxResponse.userEmail = boResponse.userEmail;
        ajaxResponse.status = boResponse.status;
    }

    private void convert(UpdateUserAJAXRequest ajaxRequest, BOUpdateUserRequest boRequest) {
        boRequest.password = ajaxRequest.password;
        boRequest.status = ajaxRequest.status;
    }

    private void convert(BOUpdateUserResponse boResponse, UpdateUserAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.status = boResponse.status;
    }
}
