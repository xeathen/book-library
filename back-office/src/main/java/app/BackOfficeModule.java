package app;

import app.bo.administrator.domain.Administrator;
import app.bo.api.AuthorAJAXWebService;
import app.bo.api.BookAJAXWebService;
import app.bo.api.CategoryAJAXWebService;
import app.bo.api.TagAJAXWebService;
import app.bo.api.UserAJAXWebService;
import app.bo.author.service.AuthorService;
import app.bo.author.web.AuthorAJAXWebServiceImpl;
import app.bo.book.service.BookService;
import app.bo.book.web.BookAJAXWebServiceImpl;
import app.bo.category.service.CategoryService;
import app.bo.category.web.CategoryAJAXWebServiceImpl;
import app.bo.tag.service.TagService;
import app.bo.tag.web.TagAJAXWebServiceImpl;
import app.bo.user.service.AdminService;
import app.bo.user.service.UserService;
import app.bo.user.web.UserAJAXWebServiceImpl;
import app.book.api.BOAuthorWebService;
import app.book.api.BOBookWebService;
import app.book.api.BOCategoryWebService;
import app.book.api.BOTagWebService;
import app.user.api.BOUserWebService;
import core.framework.module.APIConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class BackOfficeModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Administrator.class);

        APIConfig api = api();
        api.client(BOUserWebService.class, requiredProperty("app.user.serviceURL"));
        api.client(BOBookWebService.class, requiredProperty("app.book.serviceURL"));
        api.client(BOAuthorWebService.class, requiredProperty("app.book.serviceURL"));
        api.client(BOCategoryWebService.class, requiredProperty("app.book.serviceURL"));
        api.client(BOTagWebService.class, requiredProperty("app.book.serviceURL"));

        bind(UserService.class);
        bind(BookService.class);
        bind(AuthorService.class);
        bind(CategoryService.class);
        bind(TagService.class);
        bind(AdminService.class);

        api.service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
        api.service(BookAJAXWebService.class, bind(BookAJAXWebServiceImpl.class));
        api.service(CategoryAJAXWebService.class, bind(CategoryAJAXWebServiceImpl.class));
        api.service(TagAJAXWebService.class, bind(TagAJAXWebServiceImpl.class));
        api.service(AuthorAJAXWebService.class, bind(AuthorAJAXWebServiceImpl.class));
    }
}
