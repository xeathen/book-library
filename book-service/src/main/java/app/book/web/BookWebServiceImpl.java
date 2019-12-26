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
import app.book.api.book.SearchHistoryResponse;
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
    public GetBookResponse get(Long id) {
        return bookService.get(id);
    }

    @Override
    public SearchBookResponse search(SearchBookRequest request) {
        if (!Strings.isBlank(request.name)) {
            ActionLogContext.put("book_name", request);
        }
        return bookService.search(request);
    }

    @Override
    public SearchHistoryResponse searchBorrowedHistory(Long userId) {
        ActionLogContext.put("user_id", userId);
        return bookService.searchBorrowedHistory(userId);
    }

    @Override
    public BorrowBookResponse borrowBook(BorrowBookRequest request) {
        ActionLogContext.put("user_id", request.userId);
        ActionLogContext.put("book_id", request.bookId);
        return bookService.borrowBook(request);
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        ActionLogContext.put("user_id", request.userId);
        ActionLogContext.put("book_id", request.bookId);
        return bookService.returnBook(request);
    }

    @Override
    public CreateReservationResponse reserve(CreateReservationRequest request) {
        ActionLogContext.put("user_id", request.userId);
        ActionLogContext.put("book_id", request.bookId);
        return bookService.reserve(request);
    }
}
