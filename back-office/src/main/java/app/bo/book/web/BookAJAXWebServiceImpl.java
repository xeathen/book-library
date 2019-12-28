package app.bo.book.web;

import app.bo.api.BookAJAXWebService;
import app.bo.api.book.CreateAuthorAJAXRequest;
import app.bo.api.book.CreateAuthorAJAXResponse;
import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.CreateCategoryAJAXRequest;
import app.bo.api.book.CreateCategoryAJAXResponse;
import app.bo.api.book.CreateTagAJAXRequest;
import app.bo.api.book.CreateTagAJAXResponse;
import app.bo.book.service.BookService;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService bookService;

    @Override
    public CreateBookAJAXResponse create(CreateBookAJAXRequest request) {
        return bookService.create(request);
    }

    @Override
    public CreateCategoryAJAXResponse createCategory(CreateCategoryAJAXRequest request) {
        return bookService.createCategory(request);
    }

    @Override
    public CreateTagAJAXResponse createTag(CreateTagAJAXRequest request) {
        return bookService.createTag(request);
    }

    @Override
    public CreateAuthorAJAXResponse createAuthor(CreateAuthorAJAXRequest request) {
        return bookService.createAuthor(request);
    }
}
