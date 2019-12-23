package app.user.api;

import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.BOUpdateUserResponse;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BOUserWebService {
    @POST
    @Path("/bo/user")
    BOCreateUserResponse create(BOCreateUserRequest request);

    @DELETE
    @Path("/bo/user/:id")
    BODeleteUserResponse delete(@PathParam("id") Long id);

    @PUT
    @Path("/bo/user/:id")
    BOUpdateUserResponse update(@PathParam("id") Long id, BOUpdateUserRequest request);
}
