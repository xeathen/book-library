package app;

import app.bo.administrator.service.AdminService;
import app.bo.api.administrator.AdminLoginAJAXRequest;
import app.bo.api.administrator.AdminLoginAJAXResponse;
import app.bo.api.administrator.LoginMessage;
import core.framework.inject.Inject;
import core.framework.web.Controller;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author Ethan
 */
public class AdminLoginController implements Controller {
    @Inject
    AdminService adminService;

    @Override
    public Response execute(Request request) throws Exception {
        AdminLoginAJAXResponse adminLoginAJAXResponse = new AdminLoginAJAXResponse();
        if (request.session().get("adminId").isPresent()) {
            adminLoginAJAXResponse.loginMessage = LoginMessage.ALREADY_LOGIN;
        } else {
            AdminLoginAJAXRequest adminLoginAJAXRequest = request.bean(AdminLoginAJAXRequest.class);
            adminLoginAJAXResponse = adminService.login(adminLoginAJAXRequest);
            if (adminLoginAJAXResponse.loginMessage == LoginMessage.SUCCESSFUL) {
                request.session().set("adminId", adminLoginAJAXResponse.id.toString());
            }
        }
        return Response.bean(adminLoginAJAXResponse);
    }
}
