package app.bo.api;

import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.UpdateUserAJAXRequest;
import app.bo.api.user.UpdateUserAJAXResponse;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserAJAXWebService {
    @POST
    @Path("/ajax/user")
    CreateUserAJAXResponse create(CreateUserAJAXRequest request);

    @DELETE
    @Path("/ajax/user/:id")
    DeleteUserAJAXResponse delete(@PathParam("id") Long id);

    @PUT
    @Path("/ajax/user/:id")
    UpdateUserAJAXResponse update(@PathParam("id") Long id, UpdateUserAJAXRequest request);

    //TODO:resetPassword
    //TODO:active/inactive
}
