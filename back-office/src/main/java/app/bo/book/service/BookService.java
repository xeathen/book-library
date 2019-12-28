package app.bo.book.service;

import app.bo.api.book.CreateAuthorAJAXRequest;
import app.bo.api.book.CreateAuthorAJAXResponse;
import app.bo.api.book.CreateBookAJAXRequest;
import app.bo.api.book.CreateBookAJAXResponse;
import app.bo.api.book.CreateCategoryAJAXRequest;
import app.bo.api.book.CreateCategoryAJAXResponse;
import app.bo.api.book.CreateTagAJAXRequest;
import app.bo.api.book.CreateTagAJAXResponse;
import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateAuthorRequest;
import app.book.api.book.BOCreateAuthorResponse;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOCreateCategoryRequest;
import app.book.api.book.BOCreateCategoryResponse;
import app.book.api.book.BOCreateTagRequest;
import app.book.api.book.BOCreateTagResponse;
import core.framework.inject.Inject;

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

    private void convert(BOCreateBookResponse boCreateBookResponse, CreateBookAJAXResponse ajaxResponse) {
        ajaxResponse.id = boCreateBookResponse.id;
        ajaxResponse.name = boCreateBookResponse.name;
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

    public void convert(BOCreateCategoryResponse boResponse, CreateCategoryAJAXResponse ajaxResponse) {
        ajaxResponse.id = boResponse.id;
        ajaxResponse.categoryName = boResponse.categoryName;
    }
}
