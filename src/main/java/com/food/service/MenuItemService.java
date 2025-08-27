package com.food.service;

import com.food.model.MenuItem;
import com.food.model.Restaurant;
import com.food.repo.MenuItemRepository;
import com.food.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    
    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
    
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
    }
    
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
    
    public List<MenuItem> searchMenuItems(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllMenuItems();
        }
        return menuItemRepository.searchMenuItems(query.trim());
    }
    
    public List<MenuItem> getMenuItemsByCategory(String category, Long restaurantId) {
        if (restaurantId != null) {
            return menuItemRepository.findByCategoryAndRestaurantId(category, restaurantId);
        }
        return menuItemRepository.findByCategory(category);
    }
    
    public List<MenuItem> getVegetarianItems(Long restaurantId) {
        if (restaurantId != null) {
            return menuItemRepository.findByIsVegTrueAndRestaurantId(restaurantId);
        }
        return menuItemRepository.findByIsVegTrue();
    }
    
    public List<MenuItem> getBestsellers(Long restaurantId) {
        if (restaurantId != null) {
            return menuItemRepository.findByBestsellerTrueAndRestaurantId(restaurantId);
        }
        return menuItemRepository.findByBestsellerTrue();
    }
    
    public List<MenuItem> getMenuItemsByPriceRange(Double minPrice, Double maxPrice, Long restaurantId) {
        if (restaurantId != null) {
            return menuItemRepository.findByPriceBetweenAndRestaurantId(minPrice, maxPrice, restaurantId);
        }
        return menuItemRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public List<MenuItem> getHighRatedItems(Double minRating) {
        return menuItemRepository.findByRatingGreaterThanEqual(minRating);
    }
    
    public List<MenuItem> getMenuItemsSortedByPrice() {
        return menuItemRepository.findAllByOrderByPriceAsc();
    }
    
    public List<MenuItem> getMenuItemsSortedByRating() {
        return menuItemRepository.findAllByOrderByRatingDesc();
    }
    
    public List<String> getAllCategories() {
        return menuItemRepository.findAllCategories();
    }
    
    public List<String> getCategoriesByRestaurant(Long restaurantId) {
        return menuItemRepository.findCategoriesByRestaurantId(restaurantId);
    }
    
    public MenuItem createMenuItem(MenuItem menuItem) {
        // Validate restaurant exists
        if (menuItem.getRestaurant() != null && menuItem.getRestaurant().getId() != null) {
            Restaurant restaurant = restaurantRepository.findById(menuItem.getRestaurant().getId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            menuItem.setRestaurant(restaurant);
        }
        return menuItemRepository.save(menuItem);
    }
    
    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        MenuItem menuItem = getMenuItemById(id);
        
        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setImage(menuItemDetails.getImage());
        menuItem.setCategory(menuItemDetails.getCategory());
        menuItem.setIsVeg(menuItemDetails.getIsVeg());
        menuItem.setBestseller(menuItemDetails.getBestseller());
        menuItem.setRating(menuItemDetails.getRating());
        
        if (menuItemDetails.getRestaurant() != null && menuItemDetails.getRestaurant().getId() != null) {
            Restaurant restaurant = restaurantRepository.findById(menuItemDetails.getRestaurant().getId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            menuItem.setRestaurant(restaurant);
        }
        
        return menuItemRepository.save(menuItem);
    }
    
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = getMenuItemById(id);
        menuItemRepository.delete(menuItem);
    }
}
