package app.library.api;

import app.library.api.book.BorrowBookAJAXRequest;
import app.library.api.book.BorrowBookAJAXResponse;
import app.library.api.book.CreateReservationAJAXRequest;
import app.library.api.book.CreateReservationAJAXResponse;
import app.library.api.book.GetBookAJAXResponse;
import app.library.api.book.ReturnBookAJAXRequest;
import app.library.api.book.ReturnBookAJAXResponse;
import app.library.api.book.SearchBookAJAXRequest;
import app.library.api.book.SearchBookAJAXResponse;
import app.library.api.book.SearchRecordByUserIdAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface BookAJAXWebService {
    @GET
    @Path("/ajax/book/:id")
    GetBookAJAXResponse get(@PathParam("id") Long id);

    @GET
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);

    @GET
    @Path("/ajax/borrowed-record/:id")
    SearchRecordByUserIdAJAXResponse searchRecordByUserId(@PathParam("id") Long userId);

    @POST
    @Path("/ajax/borrowed-record/borrow-book")
    BorrowBookAJAXResponse borrowBook(BorrowBookAJAXRequest request);

    @POST
    @Path("/ajax/borrowed-record/return-book")
    ReturnBookAJAXResponse returnBook(ReturnBookAJAXRequest request);

    @POST
    @Path("/ajax/reservation")
    CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request);
}
