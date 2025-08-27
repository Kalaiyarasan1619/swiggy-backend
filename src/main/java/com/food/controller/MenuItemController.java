package com.food.controller;

import com.food.model.MenuItem;
import com.food.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {
    
    @Autowired
    private MenuItemService menuItemService;
    
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurant(restaurantId));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(@RequestParam String query) {
        return ResponseEntity.ok(menuItemService.searchMenuItems(query));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByCategory(category, restaurantId));
    }
    
    @GetMapping("/vegetarian")
    public ResponseEntity<List<MenuItem>> getVegetarianItems(
            @RequestParam(required = false) Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getVegetarianItems(restaurantId));
    }
    
    @GetMapping("/bestsellers")
    public ResponseEntity<List<MenuItem>> getBestsellers(
            @RequestParam(required = false) Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getBestsellers(restaurantId));
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<MenuItem>> getMenuItemsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(required = false) Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByPriceRange(minPrice, maxPrice, restaurantId));
    }
    
    @GetMapping("/high-rated")
    public ResponseEntity<List<MenuItem>> getHighRatedItems(@RequestParam Double minRating) {
        return ResponseEntity.ok(menuItemService.getHighRatedItems(minRating));
    }
    
    @GetMapping("/sorted/price")
    public ResponseEntity<List<MenuItem>> getMenuItemsSortedByPrice() {
        return ResponseEntity.ok(menuItemService.getMenuItemsSortedByPrice());
    }
    
    @GetMapping("/sorted/rating")
    public ResponseEntity<List<MenuItem>> getMenuItemsSortedByRating() {
        return ResponseEntity.ok(menuItemService.getMenuItemsSortedByRating());
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(menuItemService.getAllCategories());
    }
    
    @GetMapping("/categories/restaurant/{restaurantId}")
    public ResponseEntity<List<String>> getCategoriesByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getCategoriesByRestaurant(restaurantId));
    }
    
    // Admin endpoints
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem savedMenuItem = menuItemService.createMenuItem(menuItem);
        return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable Long id, 
            @RequestBody MenuItem menuItemDetails) {
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(id, menuItemDetails);
        return ResponseEntity.ok(updatedMenuItem);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
