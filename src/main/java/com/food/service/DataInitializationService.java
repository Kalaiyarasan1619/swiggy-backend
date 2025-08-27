package com.food.service;

import com.food.model.*;
import com.food.repo.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class DataInitializationService {
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @PostConstruct
    public void init() {
        // Initialize roles
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        
        // Initialize restaurants if empty
        if (restaurantRepository.count() == 0) {
            initializeRestaurants();
        }
    }
    
    private void initializeRestaurants() {
        // Pizza Hut
        Restaurant pizzaHut = new Restaurant();
        pizzaHut.setName("Pizza Hut");
        pizzaHut.setImage("https://wallpapers.com/images/featured/pizza-hut-gqkamsjea7s76iqm.jpg");
        pizzaHut.setRating(4.2);
        pizzaHut.setDeliveryTime("25-30 mins");
        pizzaHut.setCuisine(Arrays.asList("Pizza", "Italian"));
        pizzaHut.setCostForTwo(600);
        pizzaHut.setDistance("2.5 km");
        pizzaHut.setPromoted(true);
        pizzaHut.setDiscount("50% OFF");
        pizzaHut.setDescription("Delicious pizzas with fresh ingredients and authentic Italian flavors");
        pizzaHut.setAddress("123 Food Street, MG Road, Bangalore");
        pizzaHut.setPhone("+91 9876543210");
        
        pizzaHut = restaurantRepository.save(pizzaHut);
        
        // Add menu items for Pizza Hut
        MenuItem margherita = new MenuItem();
        margherita.setName("Margherita Pizza");
        margherita.setDescription("Classic pizza with fresh mozzarella, tomatoes and basil");
        margherita.setPrice(299.0);
        margherita.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREHe66f4PjGyj38v2OAJTOvomWXlCjQuAwf4_M5PrVVZnwqc2A1PUPrDo&s");
        margherita.setCategory("Pizza");
        margherita.setIsVeg(true);
        margherita.setBestseller(true);
        margherita.setRating(4.5);
        margherita.setRestaurant(pizzaHut);
        
        // Add more restaurants and menu items similarly...
    }
}
