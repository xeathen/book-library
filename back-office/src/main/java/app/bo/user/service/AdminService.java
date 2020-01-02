package app.bo.user.service;

import app.Constants;
import app.ErrorCodes;
import app.bo.api.user.AdminLoginRequest;
import app.bo.api.user.AdminLoginResponse;
import core.framework.web.exception.ConflictException;

/**
 * @author xeathen
 */
public class AdminService {

    public AdminLoginResponse login(AdminLoginRequest request) {
        if (Constants.ADMIN_NAME.equals(request.userName)
            && Constants.ADMIN_PASSWORD.equals(request.password)) {
            AdminLoginResponse response = new AdminLoginResponse();
            //TODO:去数据库里查一遍
            response.userId = 1L;
            response.userName = "admin";
            return response;
        } else {
            throw new ConflictException("Wrong Password.", ErrorCodes.WRONG_PASSWORD);
        }
    }
}
