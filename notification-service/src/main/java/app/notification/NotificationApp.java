package app.notification;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author Ethan
 */
public class NotificationApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        load(new NotificationModule());
    }
}
