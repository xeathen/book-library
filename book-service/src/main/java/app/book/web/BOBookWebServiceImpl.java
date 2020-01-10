package app.book.web;

import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordRequest;
import app.book.api.book.BOSearchRecordResponse;
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
    public BOGetBookResponse get(Long bookId) {
        return boBookService.get(bookId);
    }

    @Override
    public BOSearchBookResponse search(BOSearchBookRequest request) {
        return boBookService.search(request);
    }

    @Override
    public BOCreateBookResponse create(BOCreateBookRequest request) {
        ActionLogContext.put("bookName", request.name);
        return boBookService.create(request);
    }

    @Override
    public BOUpdateBookResponse update(Long bookId, BOUpdateBookRequest request) {
        ActionLogContext.put("bookId", bookId);
        return boBookService.update(bookId, request);
    }

    @Override
    public BOSearchRecordResponse searchRecord(Long bookId, BOSearchRecordRequest request) {
        return boBookService.searchRecord(bookId, request);
    }
}
