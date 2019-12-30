package app;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author Ethan
 */
public class LibraryApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        http().gzip();

        load(new LibraryModule());
    }
}
