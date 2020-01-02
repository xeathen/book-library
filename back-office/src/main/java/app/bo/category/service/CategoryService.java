package app.bo.category.service;

import app.bo.api.category.CategoryView;
import app.bo.api.category.CreateCategoryAJAXRequest;
import app.bo.api.category.CreateCategoryAJAXResponse;
import app.bo.api.category.ListCategoryAJAXResponse;
import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOCreateCategoryResponse;
import app.book.api.category.BOListCategoryResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class CategoryService {
    @Inject
    BOCategoryWebService boCategoryWebService;

    @Inject


    public ListCategoryAJAXResponse list() {
        ListCategoryAJAXResponse ajaxResponse = new ListCategoryAJAXResponse();
        BOListCategoryResponse boListCategoryResponse = boCategoryWebService.list();
        ajaxResponse.categories = boListCategoryResponse.categories.stream().map(boCategoryView -> {
            CategoryView categoryView = new CategoryView();
            categoryView.categoryId = boCategoryView.categoryId;
            categoryView.categoryName = boCategoryView.categoryName;
            return categoryView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListCategoryResponse.total;
        return ajaxResponse;
    }

    public CreateCategoryAJAXResponse create(CreateCategoryAJAXRequest ajaxRequest) {
        BOCreateCategoryRequest boRequest = new BOCreateCategoryRequest();
        boRequest.categoryName = ajaxRequest.categoryName;
        return createCategoryAJAXResponse(boCategoryWebService.create(boRequest));
    }

    private CreateCategoryAJAXResponse createCategoryAJAXResponse(BOCreateCategoryResponse boResponse) {
        CreateCategoryAJAXResponse ajaxResponse = new CreateCategoryAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.categoryName = boResponse.categoryName;
        return ajaxResponse;
    }
}
