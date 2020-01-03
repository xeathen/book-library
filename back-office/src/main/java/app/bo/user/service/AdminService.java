package app.bo.user.service;

import app.bo.administrator.domain.Administrator;
import app.bo.api.administrator.AdminLoginRequest;
import app.bo.api.administrator.AdminLoginResponse;
import app.bo.api.administrator.LoginMessage;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.Optional;

/**
 * @author xeathen
 */
public class AdminService {
    @Inject
    Repository<Administrator> administratorRepository;

    public AdminLoginResponse login(AdminLoginRequest request) {
        AdminLoginResponse response = new AdminLoginResponse();
        Query<Administrator> query = administratorRepository.select();
        query.where("admin_name = ?", request.adminName);
        Optional<Administrator> administratorOptional = query.fetchOne();
        if (administratorOptional.isEmpty()) {
            response.loginMessage = LoginMessage.ADMINISTRATOR_NOT_FOUND;
        } else {
            Administrator administrator = administratorOptional.get();
            if (administrator.password.equals(hash(request.password, administrator.salt, administrator.iteration))) {
                response.adminId = administrator.id;
                response.adminName = administrator.adminName;
                response.loginMessage = LoginMessage.SUCCESSFUL;
            } else {
                response.loginMessage = LoginMessage.WRONG_PASSWORD;
            }
        }
        return response;
    }

    private String hash(String password, String salt, int iteration) {
        String hashedPassword = password;
        for (int i = 0; i < iteration; i++) {
            hashedPassword = Hash.sha256Hex(salt + hashedPassword);
        }
        return hashedPassword;
    }

    public static void main(String[] args) {
        System.out.println(new AdminService().hash("admin", "hiasdb", 6));
    }
}
