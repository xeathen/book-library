package app.bo.user.service;

import app.ErrorCodes;
import app.bo.api.user.AdminLoginRequest;
import app.bo.api.user.AdminLoginResponse;
import core.framework.web.exception.ConflictException;

/**
 * @author xeathen
 */
public class AdminService {

    public AdminLoginResponse login(AdminLoginRequest request) {
        if ("admin".equals(request.userName) && "admin".equals(request.password)) {
            AdminLoginResponse response = new AdminLoginResponse();
            response.userId = 1L;
            response.userName = "admin";
            return response;
        } else {
            throw new ConflictException("Wrong Password.", ErrorCodes.WRONG_PASSWORD);
        }
    }
}
