package app;

import app.book.api.BOTagWebService;
import app.tag.domain.Tag;
import app.tag.service.BOTagService;
import app.tag.web.BOTagWebServiceImpl;
import core.framework.module.DBConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class TagModule extends Module {
    @Override
    protected void initialize() {
        DBConfig db = db();
        db.repository(Tag.class);

        bind(BOTagService.class);
        api().service(BOTagWebService.class, bind(BOTagWebServiceImpl.class));
    }
}
