package app;

import app.bo.api.BookAJAXWebService;
import app.bo.api.UserAJAXWebService;
import app.bo.book.service.BookService;
import app.bo.book.web.BookAJAXWebServiceImpl;
import app.bo.user.service.UserService;
import app.bo.user.web.UserAJAXWebServiceImpl;
import app.book.api.BOBookWebService;
import app.user.api.BOUserWebService;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class BackOfficeModule extends Module {
    @Override
    protected void initialize() {
        api().client(BOUserWebService.class, requiredProperty("app.userWebAJAXService.URL"));
        api().client(BOBookWebService.class, requiredProperty("app.bookWebAJAXService.URL"));

        bind(UserService.class);
        bind(BookService.class);

        api().service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
        api().service(BookAJAXWebService.class, bind(BookAJAXWebServiceImpl.class));
    }
}
