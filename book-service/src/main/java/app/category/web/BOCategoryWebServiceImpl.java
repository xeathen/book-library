package app.category.web;

import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOCreateCategoryResponse;
import app.book.api.category.BOListCategoryResponse;
import app.category.service.BOCategoryService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BOCategoryWebServiceImpl implements BOCategoryWebService {
    @Inject
    BOCategoryService boCategoryService;

    @Override
    public BOListCategoryResponse list() {
        return boCategoryService.list();
    }

    @Override
    public BOCreateCategoryResponse create(BOCreateCategoryRequest request) {
        ActionLogContext.put("categoryName", request.name);
        return boCategoryService.create(request);
    }
}
