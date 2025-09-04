package com.food.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.food.dto.OrderSummaryDTO;
import com.food.model.FoodOrders;

public interface FoodOrderRepository extends JpaRepository<FoodOrders, Long> {

@Query(value = "SELECT SUM(total_amount) AS totalAmount, " +
               "COUNT(food_quantity) AS foodCount, " +
               "order_id AS orderId " +   // ✅ alias match DTO field
               "FROM food_orders fo GROUP BY order_id", 
       nativeQuery = true)
List<OrderSummaryDTO> getOrderSummary();

@Query("SELECT f FROM FoodOrders f WHERE f.order_id = :orderId")
List<FoodOrders> findByOrderId(@Param("orderId") String orderId);


}
