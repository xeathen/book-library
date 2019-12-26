package app.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import app.user.service.BOUserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BOUserWebServiceImpl implements BOUserWebService {
    @Inject
    BOUserService boUserService;

    @Override
    public BOCreateUserResponse create(BOCreateUserRequest request) {
        ActionLogContext.put("user_name", request);
        return boUserService.create(request);
    }

    @Override
    public BOUpdateUserResponse update(Long id, BOUpdateUserRequest request) {
        ActionLogContext.put("user_id", id);
        return boUserService.update(id, request);
    }

    @Override
    public BODeleteUserResponse delete(Long id) {
        ActionLogContext.put("user_id", id);
        return boUserService.delete(id);
    }
}
