package app.bo.user.service;

import app.bo.api.book.AdminLoginRequest;
import app.bo.api.book.AdminLoginResponse;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;

/**
 * @author xeathen
 */
public class AdminService {

    public AdminLoginResponse login(AdminLoginRequest request) {
        if (Strings.isBlank(request.userName)) {
            throw new BadRequestException("username can not be blank.");
        }
        if (("admin").equals(request.userName) && "admin".equals(request.password)) {
            AdminLoginResponse response = new AdminLoginResponse();
            response.userId = 1L;
            response.userName = "admin";
            return response;
        } else {
            throw new BadRequestException("password is wrong.");
        }
    }
}
