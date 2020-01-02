package app.bo.book.service;

import app.bo.api.book.BookView;
import app.bo.api.book.BorrowedRecordView;
import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.GetBookAJAXResponse;
import app.bo.api.book.SearchBookAJAXRequest;
import app.bo.api.book.SearchBookAJAXResponse;
import app.bo.api.book.SearchRecordAJAXResponse;
import app.bo.api.book.UpdateBookAJAXRequest;
import app.bo.api.book.UpdateBookAJAXResponse;
import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    BOBookWebService boBookWebService;

    public GetBookAJAXResponse get(Long bookId) {
        return getBookAJAXResponse(boBookWebService.get(bookId));
    }

    public CreateBookAJAXResponse create(CreateBookAJAXRequest ajaxRequest) {
        BOCreateBookRequest boCreateBookRequest = boCreateBookRequest(ajaxRequest);
        return createBookAJAXResponse(boBookWebService.create(boCreateBookRequest));
    }

    public SearchBookAJAXResponse search(SearchBookAJAXRequest ajaxRequest) {
        BOSearchBookRequest boSearchBookRequest = boSearchBookRequest(ajaxRequest);
        return searchBookAJAXResponse(boBookWebService.search(boSearchBookRequest));
    }

    public SearchRecordAJAXResponse searchRecordByBookId(Long bookId) {
        SearchRecordAJAXResponse ajaxResponse = new SearchRecordAJAXResponse();
        BOSearchRecordResponse boSearchRecordResponse = boBookWebService.searchRecordByBookId(bookId);
        ajaxResponse.borrowedRecords = boSearchRecordResponse.borrowedRecords.stream().map(boBorrowedRecordView -> {
            BorrowedRecordView borrowedRecordView = new BorrowedRecordView();
            borrowedRecordView.id = boBorrowedRecordView.id;
            borrowedRecordView.bookId = boBorrowedRecordView.bookId;
            borrowedRecordView.bookName = boBorrowedRecordView.bookName;
            borrowedRecordView.userId = boBorrowedRecordView.userId;
            borrowedRecordView.userName = boBorrowedRecordView.userName;
            borrowedRecordView.borrowTime = boBorrowedRecordView.borrowTime;
            borrowedRecordView.returnTime = boBorrowedRecordView.returnTime;
            borrowedRecordView.isReturned = boBorrowedRecordView.isReturned;
            return borrowedRecordView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boSearchRecordResponse.total;
        return ajaxResponse;
    }

    public UpdateBookAJAXResponse update(Long id, UpdateBookAJAXRequest ajaxRequest) {
        BOUpdateBookRequest boRequest = boUpdateBookRequest(ajaxRequest);
        return updateBookAJAXResponse(boBookWebService.update(id, boRequest));
    }

    private GetBookAJAXResponse getBookAJAXResponse(BOGetBookResponse bookResponse) {
        GetBookAJAXResponse ajaxResponse = new GetBookAJAXResponse();
        ajaxResponse.id = bookResponse.id;
        ajaxResponse.name = bookResponse.name;
        ajaxResponse.categoryName = bookResponse.categoryName;
        ajaxResponse.authorName = bookResponse.authorName;
        ajaxResponse.tagName = bookResponse.tagName;
        ajaxResponse.description = bookResponse.description;
        ajaxResponse.publishingHouse = bookResponse.publishingHouse;
        ajaxResponse.mount = bookResponse.mount;
        return ajaxResponse;
    }

    private BOSearchBookRequest boSearchBookRequest(SearchBookAJAXRequest ajaxRequest) {
        BOSearchBookRequest boSearchBookRequest = new BOSearchBookRequest();
        boSearchBookRequest.skip = ajaxRequest.skip;
        boSearchBookRequest.limit = ajaxRequest.limit;
        boSearchBookRequest.name = ajaxRequest.name;
        boSearchBookRequest.category = ajaxRequest.category;
        boSearchBookRequest.tag = ajaxRequest.tag;
        boSearchBookRequest.author = ajaxRequest.author;
        boSearchBookRequest.description = ajaxRequest.description;
        boSearchBookRequest.publishingHouse = ajaxRequest.publishingHouse;
        return boSearchBookRequest;
    }

    private UpdateBookAJAXResponse updateBookAJAXResponse(BOUpdateBookResponse boResponse) {
        UpdateBookAJAXResponse ajaxResponse = new UpdateBookAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.name = boResponse.name;
        ajaxResponse.categoryId = boResponse.categoryId;
        ajaxResponse.tagId = boResponse.tagId;
        ajaxResponse.authorId = boResponse.authorId;
        ajaxResponse.publishingHouse = boResponse.publishingHouse;
        ajaxResponse.description = boResponse.description;
        ajaxResponse.mount = boResponse.mount;
        return ajaxResponse;
    }

    private BOUpdateBookRequest boUpdateBookRequest(UpdateBookAJAXRequest ajaxRequest) {
        BOUpdateBookRequest boRequest = new BOUpdateBookRequest();
        boRequest.name = ajaxRequest.name;
        boRequest.authorId = ajaxRequest.authorId;
        boRequest.tagId = ajaxRequest.tagId;
        boRequest.categoryId = ajaxRequest.categoryId;
        boRequest.publishingHouse = ajaxRequest.publishingHouse;
        boRequest.description = ajaxRequest.description;
        boRequest.mount = ajaxRequest.mount;
        return boRequest;
    }

    private SearchBookAJAXResponse searchBookAJAXResponse(BOSearchBookResponse boResponse) {
        SearchBookAJAXResponse ajaxResponse = new SearchBookAJAXResponse();
        ajaxResponse.books = boResponse.books.stream().map(boBookView -> {
            BookView bookAJAXView = new BookView();
            bookAJAXView.id = boBookView.id;
            bookAJAXView.name = boBookView.name;
            bookAJAXView.categoryName = boBookView.categoryName;
            bookAJAXView.authorName = boBookView.authorName;
            bookAJAXView.tagName = boBookView.tagName;
            bookAJAXView.description = boBookView.description;
            bookAJAXView.publishingHouse = boBookView.publishingHouse;
            bookAJAXView.mount = boBookView.mount;
            return bookAJAXView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boResponse.total;
        return ajaxResponse;
    }

    private BOCreateBookRequest boCreateBookRequest(CreateBookAJAXRequest ajaxRequest) {
        BOCreateBookRequest boCreateBookRequest = new BOCreateBookRequest();
        boCreateBookRequest.name = ajaxRequest.name;
        boCreateBookRequest.categoryId = ajaxRequest.categoryId;
        boCreateBookRequest.authorId = ajaxRequest.authorId;
        boCreateBookRequest.tagId = ajaxRequest.tagId;
        boCreateBookRequest.description = ajaxRequest.description;
        boCreateBookRequest.publishingHouse = ajaxRequest.publishingHouse;
        boCreateBookRequest.mount = ajaxRequest.mount;
        return boCreateBookRequest;
    }

    private CreateBookAJAXResponse createBookAJAXResponse(BOCreateBookResponse boCreateBookResponse) {
        CreateBookAJAXResponse ajaxResponse = new CreateBookAJAXResponse();
        ajaxResponse.id = boCreateBookResponse.id;
        ajaxResponse.name = boCreateBookResponse.name;
        return ajaxResponse;
    }
}
