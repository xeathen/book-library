package app.user.api;

import app.user.api.user.BOChangeStatusResponse;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BODeleteUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOListUserRequest;
import app.user.api.user.BOListUserResponse;
import app.user.api.user.BOResetPasswordResponse;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserPasswordResponse;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BOUserWebService {
    @GET
    @Path("/bo/user/:id")
    BOGetUserResponse get(@PathParam("id") Long id);

    @GET
    @Path("/bo/user")
    BOListUserResponse list(BOListUserRequest request);

    @POST
    @Path("/bo/user")
    BOCreateUserResponse create(BOCreateUserRequest request);

    @DELETE
    @Path("/bo/user/:id")
    BODeleteUserResponse delete(@PathParam("id") Long id);

    @PUT
    @Path("/bo/user/:id/password")
    BOUpdateUserPasswordResponse updatePassword(@PathParam("id") Long id, BOUpdateUserPasswordRequest request);

    @PUT
    @Path("/bo/user/:id/password-reset")
    BOResetPasswordResponse resetPassword(@PathParam("id") Long id);

    @PUT
    @Path("/bo/user/:id/status")
    BOChangeStatusResponse changeStatus(@PathParam("id") Long id);
}
