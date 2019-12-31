package app;

import app.bo.api.user.AdminLoginRequest;
import app.bo.api.user.AdminLoginResponse;
import app.bo.user.web.interceptor.AdminLoginInterceptor;
import core.framework.http.HTTPMethod;
import core.framework.module.App;
import core.framework.module.SystemModule;

import java.time.Duration;

/**
 * @author Ethan
 */
public class BackOfficeApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        load(new BackOfficeModule());
        http().gzip();
        http().bean(AdminLoginRequest.class);
        http().bean(AdminLoginResponse.class);
        site().session().local();
        site().session().timeout(Duration.ofMinutes(5));
        http().route(HTTPMethod.POST, "/ajax/admin/login", bind(AdminLoginController.class));
        http().intercept(bind(AdminLoginInterceptor.class));
    }
}
