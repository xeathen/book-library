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
import app.web.api.book.SearchRecordAJAXResponse;
import app.web.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.util.Strings;

/**
 * @author Ethan
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService bookService;

    @Override
    public GetBookAJAXResponse get(Long bookId) {
        ActionLogContext.put("bookId", bookId);
        return bookService.get(bookId);
    }

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        if (!Strings.isBlank(request.name)) {
            ActionLogContext.put("bookName", request.name);
        }
        return bookService.search(request);
    }

    @Override
    public SearchRecordAJAXResponse searchRecordByUserId(Long userId) {
        ActionLogContext.put("userId", userId);
        return bookService.searchRecordByUserId(userId);
    }

    @Override
    public BorrowBookAJAXResponse borrow(BorrowBookAJAXRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.borrow(request);
    }

    @Override
    public ReturnBookAJAXResponse returnBack(ReturnBookAJAXRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.returnBack(request);
    }

    @Override
    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.reserve(request);
    }
}
