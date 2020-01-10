package app.web.api;

import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.GetBookAJAXResponse;
import app.web.api.book.ReturnBackBookAJAXResponse;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXRequest;
import app.web.api.book.SearchRecordAJAXResponse;
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
    GetBookAJAXResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);

    @POST
    @Path("/ajax/book/:id/borrowed-record")
    BorrowBookAJAXResponse borrow(@PathParam("id") Long id, BorrowBookAJAXRequest request);

    @GET
    @Path("/ajax/book/borrowed-record")
    SearchRecordAJAXResponse searchRecord(SearchRecordAJAXRequest request);

    @POST
    @Path("/ajax/book/:id/borrowed-record/return")
    ReturnBackBookAJAXResponse returnBack(@PathParam("id") Long id);

    @POST
    @Path("/ajax/book/:id/reservation")
    CreateReservationAJAXResponse reserve(@PathParam("id") Long id);
}