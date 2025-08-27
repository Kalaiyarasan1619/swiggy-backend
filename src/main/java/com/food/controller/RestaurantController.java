package com.food.controller;

import com.food.model.MenuItem;
import com.food.model.Restaurant;
import com.food.service.MenuItemService;
import com.food.service.RestaurantService;
// Remove PreAuthorize import for now
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private MenuItemService menuItemService;
    
    // ===== PUBLIC ENDPOINTS (NO AUTHENTICATION) =====
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }
    
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItem>> getRestaurantMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurant(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String query) {
        return ResponseEntity.ok(restaurantService.searchRestaurants(query));
    }
    
    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@PathVariable String cuisine) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisine(cuisine));
    }
    
    @GetMapping("/promoted")
    public ResponseEntity<List<Restaurant>> getPromotedRestaurants() {
        return ResponseEntity.ok(restaurantService.getPromotedRestaurants());
    }
    
    @GetMapping("/rating")
    public ResponseEntity<List<Restaurant>> getRestaurantsByRating(@RequestParam Double minRating) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByRating(minRating));
    }
    
    @GetMapping("/cost")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCost(@RequestParam Integer maxCost) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCostRange(maxCost));
    }
    
    @GetMapping("/discounts")
    public ResponseEntity<List<Restaurant>> getRestaurantsWithDiscount() {
        return ResponseEntity.ok(restaurantService.getRestaurantsWithDiscount());
    }
    
    // ===== ADMIN ENDPOINTS (AUTHENTICATION REQUIRED) - Add later =====
    /*
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }
    
    // ... other admin endpoints
    */
}
