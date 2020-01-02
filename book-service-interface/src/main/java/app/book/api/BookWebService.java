package app.book.api;

import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchRecordResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BookWebService {
    @GET
    @Path("/book/:id")
    GetBookResponse get(@PathParam("id") Long bookId);

    @GET
    @Path("/book")
    SearchBookResponse search(SearchBookRequest request);

    @GET
    @Path("/borrowed-record/:id")
    SearchRecordResponse searchRecordByUserId(@PathParam("id") Long userId);

    @POST
    @Path("/borrowed-record/borrow-book")
    BorrowBookResponse borrowBook(BorrowBookRequest request);

    @POST
    @Path("/borrowed-record/return-book")
    ReturnBookResponse returnBook(ReturnBookRequest request);

    @POST
    @Path("/reservation")
    CreateReservationResponse reserve(CreateReservationRequest request);
}
