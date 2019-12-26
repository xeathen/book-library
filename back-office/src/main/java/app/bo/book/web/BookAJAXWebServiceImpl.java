package app.bo.book.web;

import app.bo.api.BookAJAXWebService;
import app.bo.book.service.BookService;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService bookService;
}
