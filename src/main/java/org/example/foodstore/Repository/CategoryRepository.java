package org.example.foodstore.Repository;

import org.example.foodstore.FoodDao.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
