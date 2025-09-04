package com.food.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "food_orders")
public class FoodOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String food_name;
    private Long food_id;
    private Long food_quantity;
    private Double food_price;
    private String food_category;
    private String food_restaurant_name;
    private Long food_restaurant_id;
    private String food_image;
    private Boolean food_is_veg;

    private Long customer_id;
    private String customer_name;
    private String customer_email;

    private Double total_amount;
    private String order_id;
}
