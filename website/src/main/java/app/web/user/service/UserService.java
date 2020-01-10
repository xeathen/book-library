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

import java.util.Optional;

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
        Optional<String> userIdOptional = session.get("userId");
        if (userIdOptional.isPresent()) {
            Long userId = Long.valueOf(userIdOptional.get());
            if (userWebService.get(userId).username.equals(ajaxRequest.username)) {
                ajaxResponse.loginMessage = LoginMessage.ALREADY_LOGIN;
                return ajaxResponse;
            }
        }
        UserLoginRequest request = userLoginRequest(ajaxRequest);
        UserLoginResponse response = userWebService.login(request);
        ajaxResponse.userId = response.userId;
        ajaxResponse.username = response.username;
        if (response.loginMessage != null) {
            ajaxResponse.loginMessage = LoginMessage.valueOf(response.loginMessage.name());
        }
        if (ajaxResponse.loginMessage == LoginMessage.SUCCESSFUL) {
            session.set("userId", response.userId.toString());
        }
        return ajaxResponse;
    }

    private UserLoginRequest userLoginRequest(UserLoginAJAXRequest ajaxRequest) {
        UserLoginRequest request = new UserLoginRequest();
        request.username = ajaxRequest.username;
        request.password = ajaxRequest.password;
        return request;
    }
}
