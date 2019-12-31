package app.user.service;

import app.user.ErrorCodes;
import app.user.api.user.GetUserResponse;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserStatusView;
import app.user.domain.User;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.NotFoundException;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    Repository<User> userRepository;

    public GetUserResponse get(Long id) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("user not found, id=" + id, ErrorCodes.USER_NOT_FOUND));
        return convert(user);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        Query<User> query = userRepository.select();
        query.where("user_name = ?", request.userName);
        query.where("password = ?", request.password);
        User user = query.fetchOne().orElseThrow(() -> new NotFoundException("user not found", ErrorCodes.USER_NOT_FOUND));
        UserLoginResponse response = new UserLoginResponse();
        response.userId = user.id;
        response.userName = user.userName;
        return response;
    }

    private GetUserResponse convert(User user) {
        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.userName = user.userName;
        response.userEmail = user.userEmail;
        response.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
        return response;
    }
}
