package app.web.user.web.interceptor;

import app.web.api.user.LoginMessage;
import app.web.api.user.UserLoginAJAXResponse;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Ethan
 */
public class UserLoginInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        String path = invocation.context().request().path();
        logger.info("path:" + path);
        Optional<String> userIdOptional = invocation.context().request().session().get("userId");
        if (!"/ajax/user/login".equals(path) && userIdOptional.isEmpty()) {
            UserLoginAJAXResponse response = new UserLoginAJAXResponse();
            response.loginMessage = LoginMessage.NOT_LOGGED_IN;
            return Response.bean(response);
        }
        return invocation.proceed();
    }
}
