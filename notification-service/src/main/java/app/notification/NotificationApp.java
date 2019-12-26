package app.notification;

import core.framework.module.App;

/**
 * @author Ethan
 */
public class NotificationApp extends App {
    @Override
    protected void initialize() {
        loadProperties("app.properties");
        load(new NotificationModule());
    }
}
