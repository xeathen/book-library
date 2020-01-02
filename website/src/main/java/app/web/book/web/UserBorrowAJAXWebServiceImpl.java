package app.web.book.web;

import app.web.api.UserBorrowAJAXWebService;
import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.CreateReservationAJAXRequest;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.ReturnBookAJAXRequest;
import app.web.api.book.ReturnBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXResponse;
import app.web.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class UserBorrowAJAXWebServiceImpl implements UserBorrowAJAXWebService {
    @Inject
    BookService bookService;

    @Override
    public SearchRecordAJAXResponse searchRecordByUserId(Long userId) {
        ActionLogContext.put("userId", userId);
        return bookService.searchRecordByUserId(userId);
    }

    @Override
    public BorrowBookAJAXResponse borrowBook(BorrowBookAJAXRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.borrowBook(request);
    }

    @Override
    public ReturnBookAJAXResponse returnBook(ReturnBookAJAXRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.returnBook(request);
    }

    @Override
    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request) {
        ActionLogContext.put("userId", request.userId);
        ActionLogContext.put("bookId", request.bookId);
        return bookService.reserve(request);
    }
}
