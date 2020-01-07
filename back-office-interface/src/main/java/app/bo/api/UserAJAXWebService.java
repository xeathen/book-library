package app.bo.api;

import app.bo.api.user.ChangeStatusAJAXRequest;
import app.bo.api.user.ChangeStatusAJAXResponse;
import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.api.user.DeleteUserAJAXResponse;
import app.bo.api.user.GetUserAJAXResponse;
import app.bo.api.user.ListUserAJAXRequest;
import app.bo.api.user.ListUserAJAXResponse;
import app.bo.api.user.ResetPasswordAJAXResponse;
import app.bo.api.user.UpdateUserPasswordAJAXRequest;
import app.bo.api.user.UpdateUserPasswordAJAXResponse;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserAJAXWebService {
    @GET
    @Path("/ajax/user/:id")
    GetUserAJAXResponse get(@PathParam("id") Long id);

    @GET
    @Path("/ajax/user")
    ListUserAJAXResponse list(ListUserAJAXRequest request);

    @POST
    @Path("/ajax/user")
    CreateUserAJAXResponse create(CreateUserAJAXRequest request);

    @DELETE
    @Path("/ajax/user/:id")
    DeleteUserAJAXResponse delete(@PathParam("id") Long id);

    @PUT
    @Path("/ajax/user/:id/password")
    UpdateUserPasswordAJAXResponse updatePassword(@PathParam("id") Long id, UpdateUserPasswordAJAXRequest request);

    //TODO:encript bug
    @PUT
    @Path("/ajax/user/:id/password-reset")
    ResetPasswordAJAXResponse resetPassword(@PathParam("id") Long id);

    @PUT
    @Path("/ajax/user/:id/status")
    ChangeStatusAJAXResponse changeStatus(@PathParam("id") Long id, ChangeStatusAJAXRequest request);
}
