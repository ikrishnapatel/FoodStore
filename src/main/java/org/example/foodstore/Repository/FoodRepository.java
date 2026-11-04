package org.example.foodstore.Repository;

import org.example.foodstore.FoodDao.Food;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Integer> {
}
