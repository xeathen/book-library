package app.user.service;

import app.user.Constants;
import app.user.ErrorCodes;
import app.user.api.user.BOChangeStatusResponse;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOListUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserView;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOUserService {
    @Inject
    Repository<User> userRepository;

    public BOListUserResponse listUser() {
        BOListUserResponse response = new BOListUserResponse();
        Query<User> query = userRepository.select();
        response.users = query.fetch().stream().map(user -> {
            UserView userView = new UserView();
            userView.id = user.id;
            userView.userName = user.userName;
            userView.email = user.email;
            userView.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
            return userView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateUserResponse create(BOCreateUserRequest request) {
        Query<User> query = userRepository.select();
        query.where("user_name = ? ", request.userName);
        if (!query.fetch().isEmpty()) {
            throw new ConflictException("find duplicate username", ErrorCodes.DUPLICATE_USERNAME);
        }
        query = userRepository.select();
        query.where("email = ?", request.email);
        if (!query.fetch().isEmpty()) {
            throw new ConflictException("find duplicate email", ErrorCodes.DUPLICATE_EMAIL);
        }
        BOCreateUserResponse response = boCreateUserResponse(request);
        response.id = userRepository.insert(user(request)).orElseThrow();
        return response;
    }

    public BOUpdateUserResponse update(Long id, BOUpdateUserRequest request) {
        User user = checkUser(id);
        BOUpdateUserResponse response = new BOUpdateUserResponse();
        User temp = user(request);
        temp.id = id;
        userRepository.partialUpdate(temp);
        response.id = id;
        response.userName = Strings.isBlank(request.userName) ? user.userName : request.userName;
        response.email = Strings.isBlank(request.email) ? user.email : request.email;
        return response;
    }

    public BODeleteUserResponse delete(Long id) {
        checkUser(id);
        BODeleteUserResponse response = new BODeleteUserResponse();
        userRepository.delete(id);
        response.id = id;
        return response;
    }

    public BOResetPasswordResponse resetPassword(Long id) {
        BOResetPasswordResponse response = new BOResetPasswordResponse();
        User user = checkUser(id);
        User temp = new User();
        temp.id = id;
        temp.password = Constants.PASSWORD_RESET;
        userRepository.partialUpdate(temp);
        response.userId = id;
        response.userName = user.userName;
        return response;
    }

    public BOChangeStatusResponse changeStatus(Long id) {
        BOChangeStatusResponse response = new BOChangeStatusResponse();
        User user = checkUser(id);
        User temp = new User();
        temp.id = id;
        temp.status = user.status == UserStatus.ACTIVE ? UserStatus.INACTIVE : UserStatus.ACTIVE;
        userRepository.partialUpdate(temp);
        response.userId = id;
        response.userName = user.userName;
        response.status = user.status == null ? null : UserStatusView.valueOf(temp.status.name());
        return response;
    }

    private User checkUser(Long id) {
        Optional<User> userOptional = userRepository.get(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("user not found", ErrorCodes.USER_NOT_FOUND);
        }
        return userOptional.get();
    }

    private User user(BOUpdateUserRequest request) {
        User user = new User();
        user.userName = request.userName;
        user.email = request.email;
        return user;
    }

    private User user(BOCreateUserRequest request) {
        User user = new User();
        user.userName = request.userName;
        user.password = request.password;
        user.email = request.email;
        user.status = UserStatus.valueOf(request.status.name());
        return user;
    }

    private BOCreateUserResponse boCreateUserResponse(BOCreateUserRequest request) {
        BOCreateUserResponse response = new BOCreateUserResponse();
        response.userName = request.userName;
        response.password = request.password;
        response.email = request.email;
        response.status = request.status;
        return response;
    }
}
