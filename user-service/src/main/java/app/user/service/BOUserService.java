package app.user.service;

import app.user.ErrorCodes;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import app.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * @author Ethan
 */
public class BOUserService {
    @Inject
    Repository<User> userRepository;

    public BOCreateUserResponse create(BOCreateUserRequest request) {
        BOCreateUserResponse response = new BOCreateUserResponse();
        List<User> selectUserName = userRepository.select("user_name = ? ", request.userName);
        if (!selectUserName.isEmpty()) {
            throw new ConflictException("find duplicate username", ErrorCodes.DUPLICATE_USERNAME);
        }
        List<User> selectUserEmail = userRepository.select("user_email = ?", request.userEmail);
        if (!selectUserEmail.isEmpty()) {
            throw new ConflictException("find duplicate email", ErrorCodes.DUPLICATE_EMAIL);
        }
        response.id = userRepository.insert(convert(request)).orElseThrow();
        convert(request, response);
        return response;
    }

    public BOUpdateUserResponse update(Long id, BOUpdateUserRequest request) {
        checkUser(id);
        BOUpdateUserResponse response = new BOUpdateUserResponse();
        User user = convert(request);
        user.id = id;
        userRepository.partialUpdate(user);
        response.id = id;
        response.status = request.status;
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
        Optional<User> userOptional = checkUser(id);
        User user = new User();
        user.id = id;
        user.password = "123456";
        userRepository.partialUpdate(user);
        response.userId = id;
        response.userName = userOptional.get().userName;
        return response;
    }

    private Optional<User> checkUser(Long id){
        Optional<User> userOptional = userRepository.get(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("user not found", ErrorCodes.USER_NOT_FOUND);
        }
        return userOptional;
    }

    private User convert(BOUpdateUserRequest request) {
        User user = new User();
        user.password = request.password;
        user.status = request.status;
        return user;
    }

    private User convert(BOCreateUserRequest request) {
        User user = new User();
        user.userName = request.userName;
        user.password = request.password;
        user.userEmail = request.userEmail;
        user.status = request.status;
        return user;
    }

    private void convert(BOCreateUserRequest request, BOCreateUserResponse response) {
        response.userName = request.userName;
        response.password = request.password;
        response.userEmail = request.userEmail;
        response.status = request.status;
    }
}
