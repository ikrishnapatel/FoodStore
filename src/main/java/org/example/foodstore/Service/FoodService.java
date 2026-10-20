package org.example.foodstore.Service;

import org.example.foodstore.FoodDao.Food;
import org.example.foodstore.Repository.FoodRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }


 
}
