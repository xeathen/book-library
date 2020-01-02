package app.bo.book.service;

import app.bo.api.book.AuthorView;
import app.bo.api.book.BookView;
import app.bo.api.book.BorrowedRecordView;
import app.bo.api.book.CategoryView;
import app.bo.api.book.CreateAuthorAJAXRequest;
import app.bo.api.book.CreateAuthorAJAXResponse;
import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.CreateCategoryAJAXRequest;
import app.bo.api.book.CreateCategoryAJAXResponse;
import app.bo.api.book.CreateTagAJAXRequest;
import app.bo.api.book.CreateTagAJAXResponse;
import app.bo.api.book.GetBookAJAXResponse;
import app.bo.api.book.ListAuthorAJAXResponse;
import app.bo.api.book.ListCategoryAJAXResponse;
import app.bo.api.book.ListTagAJAXResponse;
import app.bo.api.book.SearchBookAJAXRequest;
import app.bo.api.book.SearchBookAJAXResponse;
import app.bo.api.book.SearchRecordAJAXResponse;
import app.bo.api.book.TagView;
import app.bo.api.book.UpdateBookAJAXRequest;
import app.bo.api.book.UpdateBookAJAXResponse;
import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateAuthorRequest;
import app.book.api.book.BOCreateAuthorResponse;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOCreateCategoryRequest;
import app.book.api.book.BOCreateCategoryResponse;
import app.book.api.book.BOCreateTagRequest;
import app.book.api.book.BOCreateTagResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOListAuthorResponse;
import app.book.api.book.BOListCategoryResponse;
import app.book.api.book.BOListTagResponse;
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

    public CreateCategoryAJAXResponse createCategory(CreateCategoryAJAXRequest ajaxRequest) {
        BOCreateCategoryRequest boRequest = new BOCreateCategoryRequest();
        boRequest.categoryName = ajaxRequest.categoryName;
        return createCategoryAJAXResponse(boBookWebService.createCategory(boRequest));
    }

    public CreateTagAJAXResponse createTag(CreateTagAJAXRequest ajaxRequest) {
        BOCreateTagRequest boRequest = new BOCreateTagRequest();
        boRequest.tagName = ajaxRequest.tagName;
        return createTagAJAXResponse(boBookWebService.createTag(boRequest));
    }

    public CreateAuthorAJAXResponse createAuthor(CreateAuthorAJAXRequest ajaxRequest) {
        BOCreateAuthorRequest boRequest = new BOCreateAuthorRequest();
        boRequest.authorName = ajaxRequest.authorName;
        return createAuthorAJAXResponse(boBookWebService.createAuthor(boRequest));
    }

    public ListCategoryAJAXResponse listCategory() {
        ListCategoryAJAXResponse ajaxResponse = new ListCategoryAJAXResponse();
        BOListCategoryResponse boListCategoryResponse = boBookWebService.listCategory();
        ajaxResponse.categories = boListCategoryResponse.categories.stream().map(boCategoryView -> {
            CategoryView categoryView = new CategoryView();
            categoryView.categoryId = boCategoryView.categoryId;
            categoryView.categoryName = boCategoryView.categoryName;
            return categoryView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListCategoryResponse.total;
        return ajaxResponse;
    }

    public ListTagAJAXResponse listTag() {
        ListTagAJAXResponse ajaxResponse = new ListTagAJAXResponse();
        BOListTagResponse boListTagResponse = boBookWebService.listTag();
        ajaxResponse.tags = boListTagResponse.tags.stream().map(boTagView -> {
            TagView tagView = new TagView();
            tagView.tagId = boTagView.tagId;
            tagView.tagName = boTagView.tagName;
            return tagView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListTagResponse.total;
        return ajaxResponse;
    }

    public ListAuthorAJAXResponse listAuthor() {
        ListAuthorAJAXResponse ajaxResponse = new ListAuthorAJAXResponse();
        BOListAuthorResponse boListAuthorResponse = boBookWebService.listAuthor();
        ajaxResponse.authors = boListAuthorResponse.authors.stream().map(boAuthorView -> {
            AuthorView authorView = new AuthorView();
            authorView.authorId = boAuthorView.authorId;
            authorView.authorName = boAuthorView.authorName;
            return authorView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListAuthorResponse.total;
        return ajaxResponse;
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

    private CreateAuthorAJAXResponse createAuthorAJAXResponse(BOCreateAuthorResponse boResponse) {
        CreateAuthorAJAXResponse ajaxResponse = new CreateAuthorAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.authorName = boResponse.authorName;
        return ajaxResponse;
    }

    private CreateTagAJAXResponse createTagAJAXResponse(BOCreateTagResponse boResponse) {
        CreateTagAJAXResponse ajaxResponse = new CreateTagAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.tagName = boResponse.tagName;
        return ajaxResponse;
    }

    private CreateBookAJAXResponse createBookAJAXResponse(BOCreateBookResponse boCreateBookResponse) {
        CreateBookAJAXResponse ajaxResponse = new CreateBookAJAXResponse();
        ajaxResponse.id = boCreateBookResponse.id;
        ajaxResponse.name = boCreateBookResponse.name;
        return ajaxResponse;
    }

    private CreateCategoryAJAXResponse createCategoryAJAXResponse(BOCreateCategoryResponse boResponse) {
        CreateCategoryAJAXResponse ajaxResponse = new CreateCategoryAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.categoryName = boResponse.categoryName;
        return ajaxResponse;
    }
}
