package app.bo.book.web;

import app.bo.api.BookAJAXWebService;
import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.GetBookAJAXResponse;
import app.bo.api.book.SearchBookAJAXRequest;
import app.bo.api.book.SearchBookAJAXResponse;
import app.bo.api.book.SearchRecordAJAXRequest;
import app.bo.api.book.SearchRecordAJAXResponse;
import app.bo.api.book.UpdateBookAJAXRequest;
import app.bo.api.book.UpdateBookAJAXResponse;
import app.bo.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService bookService;

    @Override
    public GetBookAJAXResponse get(Long bookId) {
        return bookService.get(bookId);
    }

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return bookService.search(request);
    }

    @Override
    public CreateBookAJAXResponse create(CreateBookAJAXRequest request) {
        ActionLogContext.put("bookName", request.name);
        return bookService.create(request);
    }

    @Override
    public UpdateBookAJAXResponse update(Long bookId, UpdateBookAJAXRequest request) {
        ActionLogContext.put("bookId", bookId);
        return bookService.update(bookId, request);
    }

    @Override
    public SearchRecordAJAXResponse searchRecord(Long id, SearchRecordAJAXRequest request) {
        return bookService.searchRecord(id, request);
    }
}
