package app.library.book.web;

import app.library.api.UserBorrowAJAXWebService;
import app.library.api.book.BorrowBookAJAXRequest;
import app.library.api.book.BorrowBookAJAXResponse;
import app.library.api.book.CreateReservationAJAXRequest;
import app.library.api.book.CreateReservationAJAXResponse;
import app.library.api.book.ReturnBookAJAXRequest;
import app.library.api.book.ReturnBookAJAXResponse;
import app.library.api.book.SearchRecordByUserIdAJAXResponse;
import app.library.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class UserBorrowAJAXWebServiceImpl implements UserBorrowAJAXWebService {
    @Inject
    BookService bookService;

    @Override
    public SearchRecordByUserIdAJAXResponse searchRecordByUserId(Long userId) {
        ActionLogContext.put("user_id", userId);
        return bookService.searchRecordByUserId(userId);
    }

    @Override
    public BorrowBookAJAXResponse borrowBook(BorrowBookAJAXRequest request) {
        ActionLogContext.put("user_id", request.userId);
        ActionLogContext.put("book_id", request.bookId);
        return bookService.borrowBook(request);
    }

    @Override
    public ReturnBookAJAXResponse returnBook(ReturnBookAJAXRequest request) {
        ActionLogContext.put("user_id", request.userId);
        ActionLogContext.put("book_id", request.bookId);
        return bookService.returnBook(request);
    }

    @Override
    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest request) {
        ActionLogContext.put("user_id", request.userId);
        ActionLogContext.put("book_id", request.bookId);
        return bookService.reserve(request);
    }
}
