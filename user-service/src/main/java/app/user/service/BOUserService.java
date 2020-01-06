package app.user.service;

import app.user.Constants;
import app.user.ErrorCodes;
import app.user.api.user.BOChangeStatusResponse;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOListUserRequest;
import app.user.api.user.BOListUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserView;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Randoms;
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

    public BOGetUserResponse get(Long id) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("User not found, id=" + id, ErrorCodes.USER_NOT_FOUND));
        return getUserResponse(user);
    }

    public BOListUserResponse list(BOListUserRequest request) {
        BOListUserResponse response = new BOListUserResponse();
        Query<User> query = userRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        response.users = query.fetch().stream().map(this::userview).collect(Collectors.toList());
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
        User user = user(request);
        String salt = Randoms.alphaNumeric(6);
        user.salt = salt;
        int iteration = Randoms.nextInt(0, 9);
        user.password = hash(request.password, salt, iteration);
        user.iteration = iteration;
        BOCreateUserResponse response = boCreateUserResponse(request);
        response.id = userRepository.insert(user).orElseThrow();
        return response;
    }

    public BOUpdateUserResponse update(Long id, BOUpdateUserRequest request) {
        User user = checkUser(id);
        convert(user, request);
        user.id = id;
        int iteration = Randoms.nextInt(0, 9);
        user.iteration = iteration;
        String salt = Randoms.alphaNumeric(6);
        user.salt = salt;
        user.password = hash(request.password, salt, iteration);
        userRepository.partialUpdate(user);
        BOUpdateUserResponse response = new BOUpdateUserResponse();
        response.id = id;
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
        int iteration = Randoms.nextInt(0, 9);
        user.iteration = iteration;
        String salt = Randoms.alphaNumeric(6);
        user.salt = salt;
        user.password = hash(Constants.PASSWORD_RESET, salt, iteration);
        userRepository.partialUpdate(user);
        response.userId = id;
        response.userName = user.userName;
        return response;
    }

    public BOChangeStatusResponse changeStatus(Long id) {
        BOChangeStatusResponse response = new BOChangeStatusResponse();
        User user = checkUser(id);
        user.status = user.status == UserStatus.ACTIVE ? UserStatus.INACTIVE : UserStatus.ACTIVE;
        userRepository.partialUpdate(user);
        response.userId = id;
        response.userName = user.userName;
        response.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
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

    private UserView userview(User user) {
        UserView userView = new UserView();
        userView.id = user.id;
        userView.userName = user.userName;
        userView.email = user.email;
        userView.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
        return userView;
    }

    private User checkUser(Long id) {
        Optional<User> userOptional = userRepository.get(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found.", ErrorCodes.USER_NOT_FOUND);
        }
        return userOptional.get();
    }

    private User user(BOCreateUserRequest request) {
        User user = new User();
        user.userName = request.userName;
        user.email = request.email;
        user.status = UserStatus.valueOf(request.status.name());
        return user;
    }

    private void convert(User user, BOUpdateUserRequest request) {
        if (!Strings.isBlank(request.userName)) {
            user.userName = request.userName;
        }
        if (!Strings.isBlank(request.email)) {
            user.email = request.email;
        }
        if (!Strings.isBlank(request.password)) {
            user.password = request.password;
        }
    }

    private BOCreateUserResponse boCreateUserResponse(BOCreateUserRequest request) {
        BOCreateUserResponse response = new BOCreateUserResponse();
        response.userName = request.userName;
        response.email = request.email;
        response.status = request.status;
        return response;
    }
}
