package app.user.service;

import app.user.Constants;
import app.user.ErrorCodes;
import app.user.api.user.BOChangeStatusRequest;
import app.user.api.user.BOChangeStatusResponse;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOListUserRequest;
import app.user.api.user.BOListUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserPasswordResponse;
import app.user.api.user.UserStatusView;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Randoms;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

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
        Query<User> query = userRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        BOListUserResponse response = new BOListUserResponse();
        response.users = query.fetch().stream().map(this::user).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateUserResponse create(BOCreateUserRequest request) {
        Query<User> query = userRepository.select();
        query.where("username = ? ", request.username);
        if (!query.fetch().isEmpty()) {
            throw new ConflictException("Find duplicate user name.", ErrorCodes.DUPLICATE_USERNAME);
        }
        query = userRepository.select();
        query.where("email = ?", request.email);
        if (!query.fetch().isEmpty()) {
            throw new ConflictException("Find duplicate email.", ErrorCodes.DUPLICATE_EMAIL);
        }
        User user = user(request);
        String salt = Randoms.alphaNumeric(6);
        user.salt = salt;
        int iteration = Randoms.nextInt(0, 9);
        user.password = hash(request.password, salt, iteration);
        user.iteration = iteration;
        long id = userRepository.insert(user).orElseThrow();
        BOCreateUserResponse response = boCreateUserResponse(request);
        response.id = id;
        return response;
    }

    public BOUpdateUserPasswordResponse updatePassword(Long id, BOUpdateUserPasswordRequest request) {
        User user = checkUser(id);
        user.id = id;
        user.password = request.password;
        int iteration = Randoms.nextInt(0, 9);
        user.iteration = iteration;
        String salt = Randoms.alphaNumeric(6);
        user.salt = salt;
        user.password = hash(request.password, salt, iteration);
        userRepository.partialUpdate(user);
        BOUpdateUserPasswordResponse response = new BOUpdateUserPasswordResponse();
        response.username = user.username;
        return response;
    }

    public BOResetPasswordResponse resetPassword(Long id) {
        User user = checkUser(id);
        int iteration = Randoms.nextInt(0, 9);
        user.iteration = iteration;
        String salt = Randoms.alphaNumeric(6);
        user.salt = salt;
        user.password = hash(Constants.PASSWORD_RESET, salt, iteration);
        userRepository.partialUpdate(user);
        BOResetPasswordResponse response = new BOResetPasswordResponse();
        response.username = user.username;
        return response;
    }

    public BOChangeStatusResponse changeStatus(Long id, BOChangeStatusRequest request) {
        User user = checkUser(id);
        user.status = UserStatus.valueOf(request.status.name());
        userRepository.partialUpdate(user);
        BOChangeStatusResponse response = new BOChangeStatusResponse();
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

    private BOGetUserResponse getUserResponse(User user) {
        BOGetUserResponse response = new BOGetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());
        return response;
    }

    private User checkUser(Long id) {
        return userRepository.get(id).orElseThrow(() -> new NotFoundException("User not found, id=" + id, ErrorCodes.USER_NOT_FOUND));
    }

    private BOListUserResponse.User user(User user) {
        BOListUserResponse.User userView = new BOListUserResponse.User();
        userView.id = user.id;
        userView.username = user.username;
        userView.email = user.email;
        userView.status = UserStatusView.valueOf(user.status.name());
        return userView;
    }

    private User user(BOCreateUserRequest request) {
        User user = new User();
        user.username = request.username;
        user.email = request.email;
        user.status = UserStatus.valueOf(request.status.name());
        return user;
    }

    private BOCreateUserResponse boCreateUserResponse(BOCreateUserRequest request) {
        BOCreateUserResponse response = new BOCreateUserResponse();
        response.username = request.username;
        response.email = request.email;
        response.status = request.status;
        return response;
    }
}
