package app.bo.book.service;

import app.bo.api.book.AuthorAJAXView;
import app.bo.api.book.BookAJAXView;
import app.bo.api.book.BorrowedRecordView;
import app.bo.api.book.CategoryAJAXView;
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
import app.bo.api.book.TagAJAXView;
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
import app.book.api.book.BOListAuthorResponse;
import app.book.api.book.BOListCategoryResponse;
import app.book.api.book.BOListTagResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.api.book.BookView;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    BOBookWebService bookWebService;

    public BookAJAXView get(Long id) {
        BookAJAXView ajaxView = new BookAJAXView();
        convert(bookWebService.get(id), ajaxView);
        return ajaxView;
    }

    public CreateBookAJAXResponse create(CreateBookAJAXRequest ajaxRequest) {
        BOCreateBookRequest boCreateBookRequest = new BOCreateBookRequest();
        convert(ajaxRequest, boCreateBookRequest);
        CreateBookAJAXResponse ajaxResponse = new CreateBookAJAXResponse();
        convert(bookWebService.create(boCreateBookRequest), ajaxResponse);
        return ajaxResponse;
    }

    public SearchBookAJAXResponse search(SearchBookAJAXRequest ajaxRequest) {
        BOSearchBookRequest boSearchBookRequest = new BOSearchBookRequest();
        convert(ajaxRequest, boSearchBookRequest);
        SearchBookAJAXResponse ajaxResponse = new SearchBookAJAXResponse();
        convert(bookWebService.search(boSearchBookRequest), ajaxResponse);
        return ajaxResponse;
    }

    private void convert(SearchBookAJAXRequest ajaxRequest, BOSearchBookRequest boSearchBookRequest) {
        boSearchBookRequest.skip = ajaxRequest.skip;
        boSearchBookRequest.limit = ajaxRequest.limit;
        boSearchBookRequest.name = ajaxRequest.name;
        boSearchBookRequest.category = ajaxRequest.category;
        boSearchBookRequest.tag = ajaxRequest.tag;
        boSearchBookRequest.author = ajaxRequest.author;
        boSearchBookRequest.description = ajaxRequest.description;
        boSearchBookRequest.pub = ajaxRequest.pub;
    }

    public CreateCategoryAJAXResponse createCategory(CreateCategoryAJAXRequest ajaxRequest) {
        BOCreateCategoryRequest boRequest = new BOCreateCategoryRequest();
        boRequest.categoryName = ajaxRequest.categoryName;
        CreateCategoryAJAXResponse ajaxResponse = new CreateCategoryAJAXResponse();
        convert(bookWebService.createCategory(boRequest), ajaxResponse);
        return ajaxResponse;
    }

    public CreateTagAJAXResponse createTag(CreateTagAJAXRequest ajaxRequest) {
        BOCreateTagRequest boRequest = new BOCreateTagRequest();
        boRequest.tagName = ajaxRequest.tagName;
        CreateTagAJAXResponse ajaxResponse = new CreateTagAJAXResponse();
        convert(bookWebService.createTag(boRequest), ajaxResponse);
        return ajaxResponse;
    }

    public CreateAuthorAJAXResponse createAuthor(CreateAuthorAJAXRequest ajaxRequest) {
        BOCreateAuthorRequest boRequest = new BOCreateAuthorRequest();
        boRequest.authorName = ajaxRequest.authorName;
        CreateAuthorAJAXResponse ajaxResponse = new CreateAuthorAJAXResponse();
        convert(bookWebService.createAuthor(boRequest), ajaxResponse);
        return ajaxResponse;
    }

    public ListCategoryAJAXResponse listCategory() {
        ListCategoryAJAXResponse ajaxResponse = new ListCategoryAJAXResponse();
        BOListCategoryResponse boListCategoryResponse = bookWebService.listCategory();
        ajaxResponse.categories = boListCategoryResponse.categories.stream().map(boCategoryView -> {
            CategoryAJAXView categoryAJAXView = new CategoryAJAXView();
            categoryAJAXView.categoryId = boCategoryView.categoryId;
            categoryAJAXView.categoryName = boCategoryView.categoryName;
            return categoryAJAXView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListCategoryResponse.total;
        return ajaxResponse;
    }

    public ListTagAJAXResponse listTag() {
        ListTagAJAXResponse ajaxResponse = new ListTagAJAXResponse();
        BOListTagResponse boListTagResponse = bookWebService.listTag();
        ajaxResponse.tags = boListTagResponse.tags.stream().map(boTagView -> {
            TagAJAXView tagAJAXView = new TagAJAXView();
            tagAJAXView.tagId = boTagView.tagId;
            tagAJAXView.tagName = boTagView.tagName;
            return tagAJAXView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListTagResponse.total;
        return ajaxResponse;
    }

    public ListAuthorAJAXResponse listAuthor() {
        ListAuthorAJAXResponse ajaxResponse = new ListAuthorAJAXResponse();
        BOListAuthorResponse boListAuthorResponse = bookWebService.listAuthor();
        ajaxResponse.authors = boListAuthorResponse.authors.stream().map(boAuthorView -> {
            AuthorAJAXView authorAJAXView = new AuthorAJAXView();
            authorAJAXView.authorId = boAuthorView.authorId;
            authorAJAXView.authorName = boAuthorView.authorName;
            return authorAJAXView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListAuthorResponse.total;
        return ajaxResponse;
    }

    public SearchRecordAJAXResponse searchRecordByBookId(Long bookId) {
        SearchRecordAJAXResponse ajaxResponse = new SearchRecordAJAXResponse();
        BOSearchRecordResponse boSearchRecordResponse = bookWebService.searchRecordByBookId(bookId);
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
        BOUpdateBookRequest boRequest = new BOUpdateBookRequest();
        convert(ajaxRequest, boRequest);
        UpdateBookAJAXResponse ajaxResponse = new UpdateBookAJAXResponse();
        convert(bookWebService.update(id, boRequest), ajaxResponse);
        return ajaxResponse;
    }

    private void convert(BookView bookView, BookAJAXView ajaxView) {
        ajaxView.id = bookView.id;
        ajaxView.name = bookView.name;
        ajaxView.categoryName = bookView.categoryName;
        ajaxView.authorName = bookView.authorName;
        ajaxView.tagName = bookView.tagName;
        ajaxView.description = bookView.description;
        ajaxView.pub = bookView.pub;
        ajaxView.num = bookView.num;

    }

    private void convert(BOUpdateBookResponse boResponse, UpdateBookAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.name = boResponse.name;
        ajaxResponse.categoryId = boResponse.categoryId;
        ajaxResponse.tagId = boResponse.tagId;
        ajaxResponse.authorId = boResponse.authorId;
        ajaxResponse.pub = boResponse.pub;
        ajaxResponse.description = boResponse.description;
        ajaxResponse.num = boResponse.num;
    }

    private void convert(UpdateBookAJAXRequest ajaxRequest, BOUpdateBookRequest boRequest) {
        boRequest.name = ajaxRequest.name;
        boRequest.authorId = ajaxRequest.authorId;
        boRequest.tagId = ajaxRequest.tagId;
        boRequest.categoryId = ajaxRequest.categoryId;
        boRequest.pub = ajaxRequest.pub;
        boRequest.description = ajaxRequest.description;
        boRequest.num = ajaxRequest.num;
    }

    private void convert(BOSearchBookResponse boResponse, SearchBookAJAXResponse ajaxResponse) {
        ajaxResponse.books = boResponse.books.stream().map(boBookView -> {
            BookAJAXView bookAJAXView = new BookAJAXView();
            bookAJAXView.id = boBookView.id;
            bookAJAXView.name = boBookView.name;
            bookAJAXView.categoryName = boBookView.categoryName;
            bookAJAXView.authorName = boBookView.authorName;
            bookAJAXView.tagName = boBookView.tagName;
            bookAJAXView.description = boBookView.description;
            bookAJAXView.pub = boBookView.pub;
            bookAJAXView.num = boBookView.num;
            return bookAJAXView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boResponse.total;
    }

    private void convert(CreateBookAJAXRequest ajaxRequest, BOCreateBookRequest boCreateBookRequest) {
        boCreateBookRequest.name = ajaxRequest.name;
        boCreateBookRequest.categoryId = ajaxRequest.categoryId;
        boCreateBookRequest.authorId = ajaxRequest.authorId;
        boCreateBookRequest.tagId = ajaxRequest.tagId;
        boCreateBookRequest.description = ajaxRequest.description;
        boCreateBookRequest.pub = ajaxRequest.pub;
        boCreateBookRequest.num = ajaxRequest.num;
    }

    private void convert(BOCreateAuthorResponse boResponse, CreateAuthorAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.authorName = boResponse.authorName;
    }

    private void convert(BOCreateTagResponse boResponse, CreateTagAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.tagName = boResponse.tagName;
    }

    private void convert(BOCreateBookResponse boCreateBookResponse, CreateBookAJAXResponse ajaxResponse) {
        ajaxResponse.id = boCreateBookResponse.id;
        ajaxResponse.name = boCreateBookResponse.name;
    }

    public void convert(BOCreateCategoryResponse boResponse, CreateCategoryAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.categoryName = boResponse.categoryName;
    }
}
