package app.user.service;

import app.user.api.user.BOGetUserResponse;
import app.user.api.user.LoginMessage;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserStatusView;
import app.user.domain.User;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.Optional;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    Repository<User> userRepository;

    public UserLoginResponse login(UserLoginRequest request) {
        UserLoginResponse response = new UserLoginResponse();
        Query<User> query = userRepository.select();
        query.where("user_name = ?", request.userName);
        Optional<User> userOptional = query.fetchOne();
        if (userOptional.isEmpty()) {
            response.loginMessage = LoginMessage.USER_NOT_FOUND;
            return response;
        }
        User user = userOptional.get();
        if (!user.password.equals(hash(request.password, user.salt, user.iteration))) {
            response.loginMessage = LoginMessage.WRONG_PASSWORD;
        } else {
            response.userId = user.id;
            response.userName = user.userName;
            response.loginMessage = LoginMessage.SUCCESSFUL;
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

    private BOGetUserResponse getUserResponse(User user) {
        BOGetUserResponse response = new BOGetUserResponse();
        response.id = user.id;
        response.userName = user.userName;
        response.email = user.email;
        response.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
        return response;
    }
}
