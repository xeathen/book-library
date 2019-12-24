package app.book.api;

import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
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
    @Path("/book")
    SearchBookResponse search(SearchBookRequest request);

    @POST
    @Path("/book/borrow-book")
    BorrowBookResponse borrowBook(BorrowBookRequest request);

    @PUT
    @Path("/book/:id")
    ReturnBookResponse returnBook(@PathParam("id") Long id);
}
