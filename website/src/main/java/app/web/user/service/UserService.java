package app.web.user.service;

import app.user.api.UserWebService;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.web.api.user.LoginMessage;
import app.web.api.user.UserLoginAJAXRequest;
import app.web.api.user.UserLoginAJAXResponse;
import core.framework.inject.Inject;
import core.framework.web.Session;
import core.framework.web.WebContext;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    UserWebService userWebService;
    @Inject
    WebContext webContext;

    public UserLoginAJAXResponse login(UserLoginAJAXRequest ajaxRequest) {
        UserLoginAJAXResponse ajaxResponse = new UserLoginAJAXResponse();
        Session session = webContext.request().session();
        if (session.get("userId").isPresent()) {
            ajaxResponse.loginMessage = LoginMessage.ALREADY_LOGIN;
        } else {
            UserLoginRequest request = userLoginRequest(ajaxRequest);
            UserLoginResponse response = userWebService.login(request);
            ajaxResponse.userId = response.userId;
            ajaxResponse.userName = response.userName;
            ajaxResponse.loginMessage = response.loginMessage == null ? null : LoginMessage.valueOf(response.loginMessage.name());
            if (ajaxResponse.loginMessage.equals(LoginMessage.SUCCESSFUL)) {
                session.set("userId", response.userId.toString());
            }
        }
        return ajaxResponse;
    }

    private UserLoginRequest userLoginRequest(UserLoginAJAXRequest ajaxRequest) {
        UserLoginRequest request = new UserLoginRequest();
        request.userName = ajaxRequest.userName;
        request.password = ajaxRequest.password;
        return request;
    }
}
