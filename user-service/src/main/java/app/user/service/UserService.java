package app.user.service;

import app.user.api.user.GetUserResponse;
import app.user.domain.User;
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
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("user not found, id=" + id));
        return convert(user);
    }

    public GetUserResponse convert(User user) {
        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.userName = user.userName;
        response.userEmail = user.userEmail;
        return response;
    }
}
