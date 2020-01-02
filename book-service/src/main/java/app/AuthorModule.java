package app;

import app.author.domain.Author;
import app.author.service.BOAuthorService;
import app.author.web.BOAuthorWebServiceImpl;
import app.book.api.BOAuthorWebService;
import core.framework.module.DBConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class AuthorModule extends Module {
    @Override
    protected void initialize() {
        DBConfig db = db();
        db.repository(Author.class);

        bind(BOAuthorService.class);
        api().service(BOAuthorWebService.class, bind(BOAuthorWebServiceImpl.class));
    }
}
