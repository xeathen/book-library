package app.web.user.service;

import app.ErrorCodes;
import app.user.api.UserWebService;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserView;
import app.web.api.user.GetUserAJAXResponse;
import app.web.api.user.UserLoginAJAXRequest;
import app.web.api.user.UserLoginAJAXResponse;
import app.web.api.user.UserStatusAJAXView;
import core.framework.api.web.service.PathParam;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Session;
import core.framework.web.WebContext;
import core.framework.web.exception.BadRequestException;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    UserWebService userWebService;
    @Inject
    WebContext webContext;

    public GetUserAJAXResponse get(@PathParam("id") Long id) {
        GetUserAJAXResponse ajaxResponse = new GetUserAJAXResponse();
        convert(userWebService.get(id), ajaxResponse);
        return ajaxResponse;
    }

    public UserLoginAJAXResponse login(UserLoginAJAXRequest ajaxRequest) {
        UserLoginAJAXResponse ajaxResponse = new UserLoginAJAXResponse();
        Session session = webContext.request().session();
        if (session.get("userId").isPresent()) {
            throw new BadRequestException("You are login already.", ErrorCodes.ALREADY_LOGIN);
        } else {
            UserLoginRequest request = new UserLoginRequest();
            convert(ajaxRequest, request);
            UserLoginResponse response = userWebService.login(request);
            if (!Strings.isBlank(response.userName)) {
                ajaxResponse.userId = response.userId;
                ajaxResponse.userName = response.userName;
            }
            session.set("userId", response.userId.toString());
            return ajaxResponse;
        }
    }

    private void convert(UserLoginAJAXRequest ajaxRequest, UserLoginRequest request) {
        request.userName = ajaxRequest.userName;
        request.password = ajaxRequest.password;
    }

    private void convert(UserView response, GetUserAJAXResponse ajaxResponse) {
        ajaxResponse.id = response.id;
        ajaxResponse.userName = response.userName;
        ajaxResponse.userEmail = response.userEmail;
        ajaxResponse.status = response.status == null ? null : UserStatusAJAXView.valueOf(response.status.name());
    }
}
