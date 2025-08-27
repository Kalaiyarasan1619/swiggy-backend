package com.food.repo;

import com.food.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
    // Find menu items by restaurant
    List<MenuItem> findByRestaurantId(Long restaurantId);
    
    // Find menu items by category
    List<MenuItem> findByCategory(String category);
    
    // Find menu items by category and restaurant
    List<MenuItem> findByCategoryAndRestaurantId(String category, Long restaurantId);
    
    // Find vegetarian items
    List<MenuItem> findByIsVegTrue();
    
    // Find vegetarian items by restaurant
    List<MenuItem> findByIsVegTrueAndRestaurantId(Long restaurantId);
    
    // Find bestsellers
    List<MenuItem> findByBestsellerTrue();
    
    // Find bestsellers by restaurant
    List<MenuItem> findByBestsellerTrueAndRestaurantId(Long restaurantId);
    
    // Search menu items by name or description
    @Query("SELECT m FROM MenuItem m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<MenuItem> searchMenuItems(@Param("query") String query);
    
    // Find items by price range
    List<MenuItem> findByPriceBetween(Double minPrice, Double maxPrice);
    
    // Find items by price range and restaurant
    List<MenuItem> findByPriceBetweenAndRestaurantId(Double minPrice, Double maxPrice, Long restaurantId);
    
    // Find items by rating greater than equal
    List<MenuItem> findByRatingGreaterThanEqual(Double rating);
    
    // Find items ordered by price (ascending)
    List<MenuItem> findAllByOrderByPriceAsc();
    
    // Find items ordered by rating (descending)
    List<MenuItem> findAllByOrderByRatingDesc();
    
    // Find distinct categories
    @Query("SELECT DISTINCT m.category FROM MenuItem m WHERE m.category IS NOT NULL")
    List<String> findAllCategories();
    
    // Find distinct categories by restaurant
    @Query("SELECT DISTINCT m.category FROM MenuItem m WHERE m.restaurant.id = :restaurantId AND m.category IS NOT NULL")
    List<String> findCategoriesByRestaurantId(@Param("restaurantId") Long restaurantId);
}
