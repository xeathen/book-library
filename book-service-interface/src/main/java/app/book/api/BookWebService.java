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
    @Path("/book/:id/borrowed-record")
    BorrowBookResponse borrow(@PathParam("id") Long id, BorrowBookRequest request);

    @POST
    @Path("/book/:id/borrowed-record/return")
    ReturnBackBookResponse returnBack(@PathParam("id") Long id, ReturnBackBookRequest request);

    @POST
    @Path("/book/:id/reservation")
    CreateReservationResponse reserve(@PathParam("id") Long id, CreateReservationRequest request);

    @GET
    @Path("/book/borrowed-record")
    SearchRecordResponse searchRecord(SearchRecordRequest request);
}
