package com.food.dto;


import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class OrderRequest {
    
    @NotNull(message = "Food ID is required")
    private Long foodId;
    
    @NotBlank(message = "Food name is required")
    private String foodName;
    
    private String description;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;
    
    private String image;
    private String category;
    private Long restaurantId;
    private String restaurantName;
    private Boolean isVeg;
    private Double rating;
    private Integer preparationTime;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @NotNull(message = "Order total is required")
    private Double orderTotal;
    
    private Double taxes;
    private Double itemTotal;
    private Double discount;
    private Double deliveryFee;
    private String orderStatus;
    private String paymentStatus;
}

// // OrderResponse.java
// package com.yourapp.dto;

// 