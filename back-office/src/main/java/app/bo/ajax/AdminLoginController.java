package app.bo.ajax;

import app.bo.api.book.AdminLoginRequest;
import app.bo.api.book.AdminLoginResponse;
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
        if (request.session().get("adminId").isPresent()) {
            return Response.text("you login already.");
        } else {
            AdminLoginRequest adminLoginRequest = request.bean(AdminLoginRequest.class);
            AdminLoginResponse adminLoginResponse = adminService.login(adminLoginRequest);
            request.session().set("adminId", adminLoginResponse.userId.toString());
            return Response.bean(adminLoginResponse);
        }
    }
}
