package app.library.interceptor;

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
            return Response.text("You should login first.");
        }
        return invocation.proceed();
    }
}
