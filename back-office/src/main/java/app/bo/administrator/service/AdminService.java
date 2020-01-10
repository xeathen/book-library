package app.bo.administrator.service;

import app.bo.administrator.domain.Administrator;
import app.bo.api.administrator.AdminLoginAJAXRequest;
import app.bo.api.administrator.AdminLoginAJAXResponse;
import app.bo.api.administrator.LoginMessage;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.Optional;

/**
 * @author Ethan
 */
public class AdminService {
    @Inject
    Repository<Administrator> administratorRepository;

    public AdminLoginAJAXResponse login(AdminLoginAJAXRequest request) {
        Query<Administrator> query = administratorRepository.select();
        query.where("admin_name = ?", request.name);
        Optional<Administrator> administratorOptional = query.fetchOne();
        AdminLoginAJAXResponse response = new AdminLoginAJAXResponse();
        if (administratorOptional.isEmpty()) {
            response.loginMessage = LoginMessage.ADMINISTRATOR_NOT_FOUND;
        } else {
            Administrator administrator = administratorOptional.get();
            if (administrator.password.equals(hash(request.password, administrator.salt, administrator.iteration))) {
                response.id = administrator.id;
                response.name = administrator.adminName;
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
}
