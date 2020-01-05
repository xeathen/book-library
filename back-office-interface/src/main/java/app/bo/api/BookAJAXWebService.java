package app.bo.api;

import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.GetBookAJAXResponse;
import app.bo.api.book.SearchBookAJAXRequest;
import app.bo.api.book.SearchBookAJAXResponse;
import app.bo.api.book.SearchRecordAJAXRequest;
import app.bo.api.book.SearchRecordAJAXResponse;
import app.bo.api.book.UpdateBookAJAXRequest;
import app.bo.api.book.UpdateBookAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BookAJAXWebService {
    @GET
    @Path("/ajax/book/:id")
    GetBookAJAXResponse get(@PathParam("id") Long bookId);

    @POST
    @Path("/ajax/book")
    CreateBookAJAXResponse create(CreateBookAJAXRequest request);

    @PUT
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);

    @PUT
    @Path("/ajax/book/:id")
    UpdateBookAJAXResponse update(@PathParam("id") Long id, UpdateBookAJAXRequest request);

    @GET
    @Path("/ajax/borrowed-record")
    SearchRecordAJAXResponse searchRecord(SearchRecordAJAXRequest recordAJAXRequest);
}
