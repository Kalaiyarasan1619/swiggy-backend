package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.dto.OrderSummaryDTO;
import com.food.model.FoodOrders;
import com.food.repo.FoodOrderRepository;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @PostMapping
    public ResponseEntity<FoodOrders> createOrder(@RequestBody FoodOrders foodOrder) {
        FoodOrders savedOrder = foodOrderRepository.save(foodOrder);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<OrderSummaryDTO>> getOrderSummary() {
        return ResponseEntity.ok(foodOrderRepository.getOrderSummary());
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<List<FoodOrders>> getOrderDetailsByOrderId(@PathVariable String order_id) {
        return ResponseEntity.ok(foodOrderRepository.findByOrderId(order_id));
    }

}
