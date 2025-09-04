package com.food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ➡️ Make sure these field names match with repository queries
    @Column(name = "food_id", nullable = false)
    private Long foodId;
    
    @Column(name = "food_name", nullable = false)
    private String foodName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "image")
    private String image;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "restaurant_id")
    private Long restaurantId;
    
    @Column(name = "restaurant_name")
    private String restaurantName;
    
    @Column(name = "is_veg")
    private Boolean isVeg;
    
    @Column(name = "rating")
    private Double rating;
    
    @Column(name = "preparation_time")
    private Integer preparationTime;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    // ➡️ This field name should match repository query
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(name = "order_total", nullable = false)
    private Double orderTotal;
    
    @Column(name = "taxes")
    private Double taxes;
    
    @Column(name = "item_total")
    private Double itemTotal;
    
    @Column(name = "discount")
    private Double discount;
    
    @Column(name = "delivery_fee")
    private Double deliveryFee;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    // ➡️ This field name should match repository query
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
