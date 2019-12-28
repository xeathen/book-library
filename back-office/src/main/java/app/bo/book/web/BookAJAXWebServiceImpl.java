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
import app.bo.api.book.ListAuthorAJAXResponse;
import app.bo.api.book.ListCategoryAJAXResponse;
import app.bo.api.book.ListTagAJAXResponse;
import app.bo.api.book.SearchBookAJAXRequest;
import app.bo.api.book.SearchBookAJAXResponse;
import app.bo.api.book.SearchRecordAJAXResponse;
import app.bo.api.book.UpdateBookAJAXRequest;
import app.bo.api.book.UpdateBookAJAXResponse;
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
//        ActionLogContext.put();
        return bookService.create(request);
    }

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return bookService.search(request);
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

    @Override
    public ListCategoryAJAXResponse listCategory() {
        return bookService.listCategory();
    }

    @Override
    public ListTagAJAXResponse listTag() {
        return bookService.listTag();
    }

    @Override
    public ListAuthorAJAXResponse listAuthor() {
        return bookService.listAuthor();
    }

    @Override
    public UpdateBookAJAXResponse update(Long id, UpdateBookAJAXRequest request) {
        return bookService.update(id, request);
    }

    @Override
    public SearchRecordAJAXResponse searchRecordByBookId(Long bookId) {
        return bookService.searchRecordByBookId(bookId);
    }
}
