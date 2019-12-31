package app;

import app.book.api.BookWebService;
import app.user.api.UserWebService;
import app.web.api.BookAJAXWebService;
import app.web.api.UserAJAXWebService;
import app.web.api.UserBorrowAJAXWebService;
import app.web.book.service.BookService;
import app.web.book.web.BookAJAXWebServiceImpl;
import app.web.book.web.UserBorrowAJAXWebServiceImpl;
import app.web.user.service.UserService;
import app.web.user.web.UserAJAXWebServiceImpl;
import core.framework.module.APIConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class LibraryModule extends Module {
    @Override
    protected void initialize() {
        APIConfig api = api();
        api.client(UserWebService.class, requiredProperty("app.user.serviceURL"));
        api.client(BookWebService.class, requiredProperty("app.book.serviceURL"));

        bind(UserService.class);
        bind(BookService.class);

        api.service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
        api.service(BookAJAXWebService.class, bind(BookAJAXWebServiceImpl.class));
        api.service(UserBorrowAJAXWebService.class, bind(UserBorrowAJAXWebServiceImpl.class));
    }
}
