package magazineandnews.service.impl;

import magazineandnews.model.Category;
import magazineandnews.repository.CategoryRepository;
import magazineandnews.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return (List<Category>)categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category technologyCategory() {
        return categoryRepository.TechnologyCategory();
    }

    @Override
    public Category fashionCategory() {
        return categoryRepository.FashionCategory();
    }
}
