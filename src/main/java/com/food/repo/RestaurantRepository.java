package com.food.repo;

import com.food.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    // Search restaurants by name or description
    @Query("SELECT r FROM Restaurant r WHERE " +
           "LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> searchRestaurants(@Param("query") String query);
    
    // Find restaurants by cuisine (since cuisine is a List<String>)
    @Query("SELECT r FROM Restaurant r JOIN r.cuisine c WHERE LOWER(c) LIKE LOWER(CONCAT('%', :cuisine, '%'))")
    List<Restaurant> findByCuisineContaining(@Param("cuisine") String cuisine);
    
    // Find promoted restaurants
    List<Restaurant> findByPromotedTrue();
    
    // Find restaurants by rating greater than or equal to specified value
    List<Restaurant> findByRatingGreaterThanEqual(Double rating);
    
    // Find restaurants by cost for two less than or equal to specified value
    List<Restaurant> findByCostForTwoLessThanEqual(Integer cost);
    
    // Find restaurants with discount
    List<Restaurant> findByDiscountIsNotNull();
    
    // Find restaurants by delivery time
    List<Restaurant> findByDeliveryTime(String deliveryTime);
    
    // Find restaurants ordered by rating (descending)
    List<Restaurant> findAllByOrderByRatingDesc();
    
    // Find restaurants ordered by cost for two (ascending)
    List<Restaurant> findAllByOrderByCostForTwoAsc();
    
    // Custom query to find restaurants by multiple cuisine types
    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.cuisine c WHERE c IN :cuisines")
    List<Restaurant> findByMultipleCuisines(@Param("cuisines") List<String> cuisines);
}
