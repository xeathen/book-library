package app;

import app.library.interceptor.UserLoginInterceptor;
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
        site().session().local();
        site().session().timeout(Duration.ofMinutes(5));
        http().intercept(bind(UserLoginInterceptor.class));
    }
}
