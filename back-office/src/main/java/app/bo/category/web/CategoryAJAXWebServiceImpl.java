package app.bo.category.web;

import app.bo.api.CategoryAJAXWebService;
import app.bo.api.category.CreateCategoryAJAXRequest;
import app.bo.api.category.CreateCategoryAJAXResponse;
import app.bo.api.category.ListCategoryAJAXResponse;
import app.bo.category.service.CategoryService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class CategoryAJAXWebServiceImpl implements CategoryAJAXWebService {
    @Inject
    CategoryService categoryService;

    @Override
    public CreateCategoryAJAXResponse create(CreateCategoryAJAXRequest request) {
        ActionLogContext.put("categoryName", request.categoryName);
        return categoryService.create(request);
    }

    @Override
    public ListCategoryAJAXResponse list() {
        return categoryService.list();
    }
}
