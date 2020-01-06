package app.book.web;

import app.book.api.BookWebService;
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
import app.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BookWebServiceImpl implements BookWebService {
    @Inject
    BookService bookService;

    @Override
    public GetBookResponse get(Long bookId) {
        return bookService.get(bookId);
    }

    @Override
    public SearchBookResponse search(SearchBookRequest request) {
        return bookService.search(request);
    }

    @Override
    public BorrowBookResponse borrow(BorrowBookRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.borrow(request);
    }

    @Override
    public ReturnBackBookResponse returnBack(ReturnBackBookRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.returnBack(request);
    }

    @Override
    public CreateReservationResponse reserve(CreateReservationRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.reserve(request);
    }

    @Override
    public SearchRecordResponse searchRecord(SearchRecordRequest request) {
        return bookService.searchRecord(request);
    }
}
