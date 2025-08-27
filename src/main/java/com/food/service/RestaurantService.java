package com.food.service;

import com.food.model.Restaurant;
import com.food.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }
    
    public List<Restaurant> searchRestaurants(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllRestaurants();
        }
        return restaurantRepository.searchRestaurants(query.trim());
    }
    
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
    
    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = getRestaurantById(id);
        
        // Update fields that exist in your model
        restaurant.setName(restaurantDetails.getName());
        restaurant.setDescription(restaurantDetails.getDescription());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhone(restaurantDetails.getPhone());
        restaurant.setImage(restaurantDetails.getImage());
        restaurant.setRating(restaurantDetails.getRating());
        restaurant.setDeliveryTime(restaurantDetails.getDeliveryTime());
        restaurant.setCostForTwo(restaurantDetails.getCostForTwo());
        restaurant.setDistance(restaurantDetails.getDistance());
        restaurant.setPromoted(restaurantDetails.getPromoted());
        restaurant.setDiscount(restaurantDetails.getDiscount());
        restaurant.setCuisine(restaurantDetails.getCuisine());
        
        return restaurantRepository.save(restaurant);
    }
    
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }
    
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineContaining(cuisine);
    }
    
    public List<Restaurant> getPromotedRestaurants() {
        return restaurantRepository.findByPromotedTrue();
    }
    
    public List<Restaurant> getRestaurantsByRating(Double minRating) {
        return restaurantRepository.findByRatingGreaterThanEqual(minRating);
    }
    
    public List<Restaurant> getRestaurantsByCostRange(Integer maxCost) {
        return restaurantRepository.findByCostForTwoLessThanEqual(maxCost);
    }
    
    public List<Restaurant> getRestaurantsWithDiscount() {
        return restaurantRepository.findByDiscountIsNotNull();
    }
}
