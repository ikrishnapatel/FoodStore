package org.example.foodstore.Service;

import org.example.foodstore.FoodDao.Category;
import org.example.foodstore.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }


    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }


}
