package app.user.service;

import app.user.ErrorCodes;
import app.user.api.user.GetUserResponse;
import app.user.api.user.LoginMessage;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserStatusView;
import app.user.domain.User;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.NotFoundException;

import java.util.Optional;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    Repository<User> userRepository;

    public GetUserResponse get(Long id) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("User not found, id=" + id, ErrorCodes.USER_NOT_FOUND));
        return getUserResponse(user);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        UserLoginResponse response = new UserLoginResponse();
        Query<User> query = userRepository.select();
        query.where("username = ?", request.username);
        Optional<User> userOptional = query.fetchOne();
        if (userOptional.isEmpty()) {
            response.loginMessage = LoginMessage.USER_NOT_FOUND;
            return response;
        }
        User user = userOptional.get();
        if (user.password.equals(hash(request.password, user.salt, user.iteration))) {
            response.userId = user.id;
            response.username = user.username;
            response.loginMessage = LoginMessage.SUCCESSFUL;
        } else {
            response.loginMessage = LoginMessage.WRONG_PASSWORD;
        }
        return response;
    }

    private GetUserResponse getUserResponse(User user) {
        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());
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
