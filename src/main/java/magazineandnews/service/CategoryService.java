package magazineandnews.service;

import magazineandnews.model.Category;

public interface CategoryService extends IService<Category> {
    Category technologyCategory();
    Category fashionCategory();
}
