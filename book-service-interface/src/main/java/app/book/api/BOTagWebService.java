package app.book.api;

import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOCreateTagResponse;
import app.book.api.tag.BOListTagResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface BOTagWebService {
    @POST
    @Path("/bo/tag")
    BOCreateTagResponse create(BOCreateTagRequest request);

    @GET
    @Path("/bo/tag")
    BOListTagResponse list();
}
