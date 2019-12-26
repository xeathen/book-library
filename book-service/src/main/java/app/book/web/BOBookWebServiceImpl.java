package app.book.web;

import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetAuthorResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOGetCategoryResponse;
import app.book.api.book.BOGetTagResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordByBookIdResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.service.BOBookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.util.Strings;

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
        if (!Strings.isBlank(request.name)) {
            ActionLogContext.put("book_name", request);
        }
        return boBookService.search(request);
    }

    @Override
    public BOGetCategoryResponse getCategories() {
        return boBookService.getCategories();
    }

    @Override
    public BOGetTagResponse getTags() {
        return boBookService.getTags();
    }

    @Override
    public BOGetAuthorResponse getAuthors() {
        return boBookService.getAuthors();
    }

    @Override
    public BOCreateBookResponse create(BOCreateBookRequest request) {
        ActionLogContext.put("book_name", request.name);
        return boBookService.create(request);
    }

    @Override
    public BOCreateBookResponse createInCategory(BOCreateBookRequest request) {
        ActionLogContext.put("book_name", request.name);
        ActionLogContext.put("book_category", request.category);
        return boBookService.createInCategory(request);
    }

    @Override
    public BOCreateBookResponse createInTag(BOCreateBookRequest request) {
        ActionLogContext.put("book_name", request.name);
        ActionLogContext.put("book_tag", request.tag);
        return boBookService.createInTag(request);
    }

    @Override
    public BOCreateBookResponse createInAuthor(BOCreateBookRequest request) {
        ActionLogContext.put("book_name", request.name);
        ActionLogContext.put("book_author", request.author);
        return boBookService.createInAuthor(request);
    }

    @Override
    public BOUpdateBookResponse update(Long id, BOUpdateBookRequest request) {
        ActionLogContext.put("book_id", id);
        ActionLogContext.put("book_name", request.name);
        return boBookService.update(id, request);
    }

    @Override
    public BOSearchRecordByBookIdResponse searchRecordByBookId(Long bookId) {
        ActionLogContext.put("book_id", bookId);
        return boBookService.searchRecordByBookId(bookId);
    }
}
