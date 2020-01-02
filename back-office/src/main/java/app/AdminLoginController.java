package app;

import app.bo.api.administrator.AdminLoginRequest;
import app.bo.api.administrator.AdminLoginResponse;
import app.bo.api.administrator.LoginMessage;
import app.bo.user.service.AdminService;
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
        AdminLoginRequest adminLoginRequest = request.bean(AdminLoginRequest.class);
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        if (request.session().get("adminId").isPresent()) {
           adminLoginResponse.loginMessage = LoginMessage.ALREADY_LOGIN;
        } else {
            adminLoginResponse = adminService.login(adminLoginRequest);
            if (adminLoginResponse.loginMessage.equals(LoginMessage.SUCCESSFUL)) {
                request.session().set("adminId", adminLoginResponse.adminId.toString());
            }
        }
        return Response.bean(adminLoginResponse);
    }
}
