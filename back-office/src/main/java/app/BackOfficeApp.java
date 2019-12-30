package app;

import app.bo.ajax.interceptor.AdminLoginInterceptor;
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
//        http().access().denyFromFile("deny-ip-list.txt");
//        http().route(HTTPMethod.POST, "/admin/login", new AdminLoginController());
//        http().intercept(bind(AdminLoginInterceptor.class));
//        site().session().timeout(Duration.ofHours(1));
    }
}
