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
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

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
        Query<User> query = userRepository.select();
        query.where("user_name = ?", request.userName);
        User user = query.fetchOne().orElseThrow(() -> new NotFoundException("User not found", ErrorCodes.USER_NOT_FOUND));
        if (!user.password.equals(request.password)) {
            throw new ConflictException("Wrong password.", ErrorCodes.WRONG_PASSWORD);
        } else {
            UserLoginResponse response = new UserLoginResponse();
            response.userId = user.id;
            response.userName = user.userName;
            return response;
        }
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
