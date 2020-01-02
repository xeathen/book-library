package app.book.web;

import app.book.api.BookWebService;
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
import app.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.util.Strings;

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
        if (!Strings.isBlank(request.name)) {
            ActionLogContext.put("bookName", request);
        }
        return bookService.search(request);
    }

    @Override
    public SearchRecordResponse searchRecordByUserId(Long userId) {
        ActionLogContext.put("userId", userId);
        return bookService.searchRecordByUserId(userId);
    }

    @Override
    public BorrowBookResponse borrowBook(BorrowBookRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.borrowBook(request);
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.returnBook(request);
    }

    @Override
    public CreateReservationResponse reserve(CreateReservationRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.reserve(request);
    }
}
