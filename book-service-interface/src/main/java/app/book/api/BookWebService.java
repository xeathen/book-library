package app.book.api;

import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBackBookRequest;
import app.book.api.book.ReturnBackBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchRecordRequest;
import app.book.api.book.SearchRecordResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BookWebService {
    @GET
    @Path("/book/:id")
    GetBookResponse get(@PathParam("id") Long bookId);

    @PUT
    @Path("/book")
    SearchBookResponse search(SearchBookRequest request);

    @POST
    @Path("/book/borrow")
    BorrowBookResponse borrow(BorrowBookRequest request);

    @POST
    @Path("/book/return-back")
    ReturnBackBookResponse returnBack(ReturnBackBookRequest request);

    @POST
    @Path("/reservation")
    CreateReservationResponse reserve(CreateReservationRequest request);

    @GET
    @Path("/borrowed-record/")
    SearchRecordResponse searchRecord(SearchRecordRequest request);
}
