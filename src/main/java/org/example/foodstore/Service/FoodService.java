package org.example.foodstore.Service;

import org.example.foodstore.FoodDao.Food;
import org.example.foodstore.Repository.CategoryRepository;
import org.example.foodstore.Repository.FoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    public FoodService(FoodRepository foodRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Transactional
    public List<Food> addFoods(List<Food> foods) {
        for (Food food : foods) {
            Integer catId = food.getCategoryId();
            if (catId != null) {
                food.setCategory(categoryRepository.getReferenceById(catId));
            }
        }
        return foodRepository.saveAll(foods);
    }
    
}
