package app.bo.book.service;

import app.bo.api.book.AuthorView;
import app.bo.api.book.BookView;
import app.bo.api.book.CategoryView;
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
import app.bo.api.book.TagView;
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
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    BOBookWebService bookWebService;

    public CreateBookAJAXResponse create(CreateBookAJAXRequest ajaxRequest) {
        BOCreateBookRequest boCreateBookRequest = new BOCreateBookRequest();
        convert(ajaxRequest, boCreateBookRequest);
        CreateBookAJAXResponse ajaxResponse = new CreateBookAJAXResponse();
        convert(bookWebService.create(boCreateBookRequest), ajaxResponse);
        return ajaxResponse;
    }

    public SearchBookAJAXResponse search(SearchBookAJAXRequest ajaxRequest) {
        BOSearchBookRequest boSearchBookRequest = new BOSearchBookRequest();
        SearchBookAJAXResponse ajaxResponse = new SearchBookAJAXResponse();
        convert(bookWebService.search(boSearchBookRequest), ajaxResponse);
        return ajaxResponse;
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
        BOListTagResponse boListTagResponse = bookWebService.listTag();
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
        BOListAuthorResponse boListAuthorResponse = bookWebService.listAuthor();
        ajaxResponse.authors = boListAuthorResponse.authors.stream().map(boAuthorView -> {
            AuthorView authorView = new AuthorView();
            authorView.authorId = boAuthorView.authorId;
            authorView.authorName = boAuthorView.authorName;
            return authorView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListAuthorResponse.total;
        return ajaxResponse;
    }

    private void convert(BOSearchBookResponse boResponse, SearchBookAJAXResponse ajaxResponse) {
        ajaxResponse.books = boResponse.books.stream().map(boBookView -> {
            BookView bookView = new BookView();
            bookView.id = boBookView.id;
            bookView.name = boBookView.name;
            bookView.categoryId = boBookView.categoryId;
            bookView.authorId = boBookView.authorId;
            bookView.tagId = boBookView.tagId;
            bookView.description = boBookView.description;
            bookView.pub = boBookView.pub;
            bookView.num = boBookView.num;
            return bookView;
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
