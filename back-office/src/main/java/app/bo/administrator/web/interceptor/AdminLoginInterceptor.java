package app.bo.administrator.web.interceptor;

import app.bo.api.administrator.AdminLoginAJAXResponse;
import app.bo.api.administrator.LoginMessage;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Ethan
 */
public class AdminLoginInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(AdminLoginInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        String path = invocation.context().request().path();
        logger.info("path:" + path);
        Optional<String> userIdOptional = invocation.context().request().session().get("adminId");
        if (!"/ajax/admin/login".equals(path) && userIdOptional.isEmpty()) {
            AdminLoginAJAXResponse response = new AdminLoginAJAXResponse();
            response.loginMessage = LoginMessage.NOT_LOGGED_IN;
            return Response.bean(response);
        }
        return invocation.proceed();
    }
}
