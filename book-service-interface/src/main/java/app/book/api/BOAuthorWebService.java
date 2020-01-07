package app.book.api;

import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOCreateAuthorResponse;
import app.book.api.author.BOListAuthorResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface BOAuthorWebService {
    @GET
    @Path("/bo/author")
    BOListAuthorResponse list();

    @POST
    @Path("/bo/author")
    BOCreateAuthorResponse create(BOCreateAuthorRequest request);
}
