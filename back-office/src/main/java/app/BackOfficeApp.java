package app;

import app.bo.administrator.web.interceptor.AdminLoginInterceptor;
import app.bo.api.administrator.AdminLoginAJAXRequest;
import app.bo.api.administrator.AdminLoginAJAXResponse;
import core.framework.http.HTTPMethod;
import core.framework.module.App;
import core.framework.module.SessionConfig;
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
        http().bean(AdminLoginAJAXRequest.class);
        http().bean(AdminLoginAJAXResponse.class);
        SessionConfig session = site().session();
        session.cookie("back-office", null);
        session.timeout(Duration.ofMinutes(30));
        http().route(HTTPMethod.PUT, "/ajax/admin/login", bind(AdminLoginController.class));
        http().intercept(bind(AdminLoginInterceptor.class));
    }
}
