package app.book.api;

import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;

/**
 * @author Ethan
 */
public interface BookWebService {
    @GET
    @Path("/book")
    SearchBookResponse search(SearchBookRequest request);

    @POST
    @Path("/book/borrow-book")
    BorrowBookResponse borrowBook(BorrowBookRequest request);

    @POST
    @Path("/book/return-book")
    ReturnBookResponse returnBook(ReturnBookRequest request);
}
