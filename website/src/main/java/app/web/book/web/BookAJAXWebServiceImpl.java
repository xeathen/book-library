package app.web.book.web;

import app.web.api.BookAJAXWebService;
import app.web.api.book.BookAJAXView;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
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
    public BookAJAXView get(Long bookId) {
        ActionLogContext.put("book_id", bookId);
        return bookService.get(bookId);
    }

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        if (!Strings.isBlank(request.name)) {
            ActionLogContext.put("book_name", request.name);
        }
        return bookService.search(request);
    }
}
