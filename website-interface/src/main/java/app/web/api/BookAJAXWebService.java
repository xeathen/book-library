package app.web.api;

import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.CreateReservationAJAXRequest;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.GetBookAJAXResponse;
import app.web.api.book.ReturnBackBookAJAXRequest;
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
    @Path("/ajax/book/borrow")
    BorrowBookAJAXResponse borrow(BorrowBookAJAXRequest request);

    @POST
    @Path("/ajax/book/return-back")
    ReturnBackBookAJAXResponse returnBack(ReturnBackBookAJAXRequest request);

    @POST
    @Path("/ajax/book/reservation")
    CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request);

    @GET
    @Path("/ajax/book/borrowed-record")
    SearchRecordAJAXResponse searchRecord(SearchRecordAJAXRequest request);
}
