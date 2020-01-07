package app.category.service;

import app.ErrorCodes;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOCreateCategoryResponse;
import app.book.api.category.BOListCategoryResponse;
import app.category.domain.Category;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOCategoryService {
    @Inject
    Repository<Category> categoryRepository;

    public BOListCategoryResponse list() {
        BOListCategoryResponse response = new BOListCategoryResponse();
        Query<Category> query = categoryRepository.select();
        response.categories = query.fetch().stream().map(category -> {
            BOListCategoryResponse.Category categoryView = new BOListCategoryResponse.Category();
            categoryView.id = category.id;
            categoryView.name = category.name;
            return categoryView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateCategoryResponse create(BOCreateCategoryRequest request) {
        if (Strings.isBlank(request.name)) {
            throw new BadRequestException("Category name must be not null.", ErrorCodes.NULL_CATEGORY);
        }
        BOCreateCategoryResponse response = new BOCreateCategoryResponse();
        Category category = new Category();
        category.name = request.name;
        response.id = (int) categoryRepository.insert(category).orElseThrow();
        response.name = request.name;
        return response;
    }
}
