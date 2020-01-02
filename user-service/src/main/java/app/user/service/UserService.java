package app.user.service;

import app.user.ErrorCodes;
import app.user.api.user.GetUserResponse;
import app.user.api.user.LoginMessage;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserStatusView;
import app.user.domain.User;
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
        //TODO:只允许get自己
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("User not found, id=" + id, ErrorCodes.USER_NOT_FOUND));
        return getUserResponse(user);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        UserLoginResponse response = new UserLoginResponse();
        Query<User> query = userRepository.select();
        query.where("user_name = ?", request.userName);
        Optional<User> userOptional = query.fetchOne();
        if (userOptional.isEmpty()){
            response.loginMessage = LoginMessage.USER_NOT_FOUND;
            return response;
        }
        User user = userOptional.get();
        if (!user.password.equals(request.password)) {
            response.loginMessage = LoginMessage.WRONG_PASSWORD;
        } else {
            response.userId = user.id;
            response.userName = user.userName;
            response.loginMessage = LoginMessage.SUCCESSFUL;
        }
        return response;
    }

    private GetUserResponse getUserResponse(User user) {
        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.userName = user.userName;
        response.email = user.email;
        response.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
        return response;
    }
}
