package app;

import app.web.user.web.interceptor.UserLoginInterceptor;
import core.framework.module.App;
import core.framework.module.SessionConfig;
import core.framework.module.SystemModule;

import java.time.Duration;

/**
 * @author Ethan
 */
public class WebsiteApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        load(new WebsiteModule());

        http().gzip();
        SessionConfig session = site().session();
        session.cookie("website", null);
        session.timeout(Duration.ofMinutes(30));
        http().intercept(bind(UserLoginInterceptor.class));
    }
}
