package com.food.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long id;
    private Long foodId;
    private String foodName;
    private String description;
    private Double price;
    private String image;
    private String category;
    private Long restaurantId;
    private String restaurantName;
    private Boolean isVeg;
    private Double rating;
    private Integer preparationTime;
    private Integer quantity;
    private Long customerId;
    private String customerName;
    private Double orderTotal;
    private Double taxes;
    private Double itemTotal;
    private Double discount;
    private Double deliveryFee;
    private String orderStatus;
    private String paymentStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime createdAt;
}

