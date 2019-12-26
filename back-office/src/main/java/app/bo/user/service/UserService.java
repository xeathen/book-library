package app.bo.user.service;

import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.UpdateUserAJAXRequest;
import app.bo.api.user.UpdateUserAJAXResponse;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
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

    public DeleteUserAJAXResponse delete(@PathParam("id") Long id) {
        return null;
    }

    public UpdateUserAJAXResponse update(@PathParam("id") Long id, UpdateUserAJAXRequest request) {
        return null;
    }

    private void convert(CreateUserAJAXRequest ajaxRequest, BOCreateUserRequest boRequest) {
        boRequest.userName = ajaxRequest.userName;
        boRequest.password = ajaxRequest.password;
        boRequest.userEmail = ajaxRequest.userEmail;
    }

    private void convert(BOCreateUserResponse boResponse, CreateUserAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.userName = boResponse.userName;
        ajaxResponse.password = boResponse.password;
        ajaxResponse.userEmail = boResponse.userEmail;
    }
}
