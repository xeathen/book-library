package app;

import app.book.api.BookWebService;
import app.library.api.BookAJAXWebService;
import app.library.api.UserAJAXWebService;
import app.library.book.service.BookService;
import app.library.book.web.BookAJAXWebServiceImpl;
import app.library.user.service.UserService;
import app.library.user.web.UserAJAXWebServiceImpl;
import app.user.api.UserWebService;
import core.framework.module.APIConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class LibraryModule extends Module {
    @Override
    protected void initialize() {
        APIConfig api = api();
        api.client(UserWebService.class, requiredProperty("app.userWebAJAXService.URL"));
        api.client(BookWebService.class, requiredProperty("app.bookWebAJAXService.URL"));

        bind(UserService.class);
        bind(BookService.class);

        api.service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
        api.service(BookAJAXWebService.class, bind(BookAJAXWebServiceImpl.class));
    }
}
