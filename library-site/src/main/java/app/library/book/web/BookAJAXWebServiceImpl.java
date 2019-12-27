package app.library.book.web;

import app.library.api.BookAJAXWebService;
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
import app.library.book.service.BookService;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService bookService;

    @Override
    public GetBookAJAXResponse get(Long id) {
        //TODO:add log
        return bookService.get(id);
    }

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return bookService.search(request);
    }

    @Override
    public SearchRecordByUserIdAJAXResponse searchRecordByUserId(Long userId) {
        return bookService.searchRecordByUserId(userId);
    }

    @Override
    public BorrowBookAJAXResponse borrowBook(BorrowBookAJAXRequest request) {
        return bookService.borrowBook(request);
    }

    @Override
    public ReturnBookAJAXResponse returnBook(ReturnBookAJAXRequest request) {
        return bookService.returnBook(request);
    }

    @Override
    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request) {
        return bookService.reserve(request);
    }
}
