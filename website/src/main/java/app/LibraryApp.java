package app;

import app.web.user.web.interceptor.UserLoginInterceptor;
import core.framework.module.App;
import core.framework.module.SystemModule;

import java.time.Duration;

/**
 * @author Ethan
 */
public class LibraryApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        load(new LibraryModule());

        http().gzip();
        site().session().timeout(Duration.ofMinutes(10));
        http().intercept(bind(UserLoginInterceptor.class));
    }
}
