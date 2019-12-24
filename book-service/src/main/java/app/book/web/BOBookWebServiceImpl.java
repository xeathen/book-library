package app.book.web;

import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetHistoryResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.service.BOBookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BOBookWebServiceImpl implements BOBookWebService {
    @Inject
    BOBookService boBookService;

    @Override
    public BOCreateBookResponse create(BOCreateBookRequest request) {
        ActionLogContext.put("book_name", request.name);
        return boBookService.create(request);
    }

    @Override
    public BOUpdateBookResponse update(Long id, BOUpdateBookRequest request) {
        ActionLogContext.put("book_id", id);
        ActionLogContext.put("book_name", request.name);
        return boBookService.update(id, request);
    }

    @Override
    public BOSearchBookResponse search(BOSearchBookRequest request) {
        return boBookService.search(request);
    }

    @Override
    public BOGetHistoryResponse getBorrowedHistory(Long bookId) {
        return boBookService.getBorrowedHistory(bookId);
    }
}
