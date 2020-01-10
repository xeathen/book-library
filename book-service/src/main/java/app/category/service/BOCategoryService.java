package app.category.service;

import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOCreateCategoryResponse;
import app.book.api.category.BOListCategoryResponse;
import app.category.domain.Category;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOCategoryService {
    @Inject
    Repository<Category> categoryRepository;

    public BOListCategoryResponse list() {
        Query<Category> query = categoryRepository.select();
        BOListCategoryResponse response = new BOListCategoryResponse();
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
        Category category = new Category();
        category.name = request.name;
        long id = categoryRepository.insert(category).orElseThrow();
        BOCreateCategoryResponse response = new BOCreateCategoryResponse();
        response.id = (int) id;
        response.name = request.name;
        return response;
    }
}
