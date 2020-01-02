package app;

import app.book.api.BOCategoryWebService;
import app.category.domain.Category;
import app.category.service.BOCategoryService;
import app.category.web.BOCategoryWebServiceImpl;
import core.framework.module.DBConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class CategoryModule extends Module {
    @Override
    protected void initialize() {
        DBConfig db = db();
        db.repository(Category.class);

        bind(BOCategoryService.class);
        api().service(BOCategoryWebService.class, bind(BOCategoryWebServiceImpl.class));
    }
}
