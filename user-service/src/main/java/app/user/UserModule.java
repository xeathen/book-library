package app.user;

import app.user.api.BOUserWebService;
import app.user.api.UserWebService;
import app.user.domain.User;
import app.user.service.BOUserService;
import app.user.service.UserService;
import app.user.web.BOUserWebServiceImpl;
import app.user.web.UserWebServiceImpl;
import core.framework.module.APIConfig;
import core.framework.module.DBConfig;
import core.framework.module.Module;

/**
 * @author Ethan
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        DBConfig db = db();
        db.repository(User.class);

        bind(UserService.class);
        bind(BOUserService.class);

        APIConfig api = api();
        api.service(UserWebService.class, bind(UserWebServiceImpl.class));
        api.service(BOUserWebService.class, bind(BOUserWebServiceImpl.class));
    }
}
