package app.library.user.service;

import app.library.api.user.GetUserAJAXResponse;
import app.user.api.UserWebService;
import app.user.api.user.GetUserResponse;
import core.framework.api.web.service.PathParam;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class UserService {
    @Inject
    UserWebService userWebService;

    public GetUserAJAXResponse get(@PathParam("id") Long id) {
        GetUserAJAXResponse ajaxResponse = new GetUserAJAXResponse();
        convert(userWebService.get(id), ajaxResponse);
        return ajaxResponse;
    }

    private void convert(GetUserResponse response, GetUserAJAXResponse ajaxResponse) {
        ajaxResponse.id = response.id;
        ajaxResponse.userName = response.userName;
        ajaxResponse.userEmail = response.userEmail;
        ajaxResponse.status = response.status;
    }
}
