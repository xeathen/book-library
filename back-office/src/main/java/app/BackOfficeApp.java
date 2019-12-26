package app;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author Ethan
 */
public class BackOfficeApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        http().gzip();
//        http().access().denyFromFile("deny-ip-list.txt");

        load(new BackOfficeModule());
    }
}
