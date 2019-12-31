package app.web.api;

import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.CreateReservationAJAXRequest;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.ReturnBookAJAXRequest;
import app.web.api.book.ReturnBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author Ethan
 */
public interface UserBorrowAJAXWebService {
    @GET
    @Path("/ajax/borrowed-record/:id")
    SearchRecordAJAXResponse searchRecordByUserId(@PathParam("id") Long userId);

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
