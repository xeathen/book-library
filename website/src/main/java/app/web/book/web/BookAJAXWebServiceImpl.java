package app.web.book.web;

import app.web.api.BookAJAXWebService;
import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.CreateReservationAJAXRequest;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.GetBookAJAXResponse;
import app.web.api.book.ReturnBookAJAXRequest;
import app.web.api.book.ReturnBookAJAXResponse;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXRequest;
import app.web.api.book.SearchRecordAJAXResponse;
import app.web.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;

/**
 * @author Ethan
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService bookService;
    @Inject
    WebContext webContext;

    @Override
    public GetBookAJAXResponse get(Long bookId) {
        return bookService.get(bookId);
    }

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return bookService.search(request);
    }

    @Override
    public BorrowBookAJAXResponse borrow(BorrowBookAJAXRequest request) {
        Long userId = Long.valueOf(webContext.request().session().get("userId").orElseThrow());
        ActionLogContext.put("userId", userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.borrow(userId, request);
    }

    @Override
    public ReturnBookAJAXResponse returnBack(ReturnBookAJAXRequest request) {
        Long userId = Long.valueOf(webContext.request().session().get("userId").orElseThrow());
        ActionLogContext.put("userId", userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.returnBack(userId, request);
    }

    @Override
    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request) {
        Long userId = Long.valueOf(webContext.request().session().get("userId").orElseThrow());
        ActionLogContext.put("userId", userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.reserve(userId, request);
    }

    @Override
    public SearchRecordAJAXResponse searchRecord(SearchRecordAJAXRequest request) {
        Long userId = Long.valueOf(webContext.request().session().get("userId").orElseThrow());
        return bookService.searchRecord(userId, request);
    }
}
