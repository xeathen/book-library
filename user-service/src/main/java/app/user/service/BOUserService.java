package app.user.service;

import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import app.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.BadRequestException;

import java.util.List;

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
            throw new BadRequestException("find duplicate username");
        }
        List<User> selectUserEmail = userRepository.select("user_email = ?", request.userEmail);
        if (!selectUserEmail.isEmpty()) {
            throw new BadRequestException("find duplicate email");
        }
        response.id = userRepository.insert(convert(request)).orElseThrow();
        convert(request, response);
        return response;
    }

    public BOUpdateUserResponse update(Long id, BOUpdateUserRequest request) {
        BOUpdateUserResponse response = new BOUpdateUserResponse();
        User user = convert(request);
        user.id = id;
        userRepository.partialUpdate(user);
        response.id = id;
        response.status = request.status;
        return response;
    }

    public BODeleteUserResponse delete(Long id) {
        BODeleteUserResponse response = new BODeleteUserResponse();
        userRepository.delete(id);
        response.id = id;
        return response;
    }

    public User convert(BOUpdateUserRequest request) {
        User user = new User();
        user.password = request.password;
        user.status = request.status;
        return user;
    }

    public User convert(BOCreateUserRequest request) {
        User user = new User();
        user.userName = request.userName;
        user.password = request.password;
        user.userEmail = request.userEmail;
        user.status = request.status;
        return user;
    }

    public void convert(BOCreateUserRequest request, BOCreateUserResponse response) {
        response.userName = request.userName;
        response.password = request.password;
        response.userEmail = request.userEmail;
        response.status = request.status;
    }
}
