// package com.food.service;

// import com.food.dto.OrderRequest;
// import com.food.dto.OrderResponse;
// import com.food.model.OrderStatus;
// import com.food.model.Orders;
// import com.food.model.PaymentStatus;
// import com.food.repo.OrderRepository;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// @Service
// @RequiredArgsConstructor
// @Slf4j
// public class OrderService {
    
//     @Autowired
//     private final OrderRepository orderRepository;
    
//     @Transactional
//     public OrderResponse createOrder(OrderRequest orderRequest, Long userId) {
//         log.info("Creating order for customer: {} with food: {}", userId, orderRequest.getFoodName());
        
//         try {
//             Orders order = new Orders();
            
//             // Map request to entity
//             order.setFoodId(orderRequest.getFoodId());
//             order.setFoodName(orderRequest.getFoodName());
//             order.setDescription(orderRequest.getDescription());
//             order.setPrice(orderRequest.getPrice());
//             order.setImage(orderRequest.getImage());
//             order.setCategory(orderRequest.getCategory());
//             order.setRestaurantId(orderRequest.getRestaurantId());
//             order.setRestaurantName(orderRequest.getRestaurantName());
//             order.setIsVeg(orderRequest.getIsVeg());
//             order.setRating(orderRequest.getRating());
//             order.setPreparationTime(orderRequest.getPreparationTime());
//             order.setQuantity(orderRequest.getQuantity());
//             order.setCustomerId(userId);
//             order.setCustomerName(orderRequest.getCustomerName());
//             order.setOrderTotal(orderRequest.getOrderTotal());
//             order.setTaxes(orderRequest.getTaxes());
//             order.setItemTotal(orderRequest.getItemTotal());
//             order.setDiscount(orderRequest.getDiscount());
//             order.setDeliveryFee(orderRequest.getDeliveryFee());
            
//             // Set status
//             if (orderRequest.getOrderStatus() != null) {
//                 order.setOrderStatus(OrderStatus.valueOf(orderRequest.getOrderStatus()));
//             } else {
//                 order.setOrderStatus(OrderStatus.PENDING);
//             }
            
//             if (orderRequest.getPaymentStatus() != null) {
//                 order.setPaymentStatus(PaymentStatus.valueOf(orderRequest.getPaymentStatus()));
//             } else {
//                 order.setPaymentStatus(PaymentStatus.PENDING);
//             }
            
//             // Set delivery date
//             order.setDeliveryDate(LocalDateTime.now().plusMinutes(
//                 orderRequest.getPreparationTime() != null ? orderRequest.getPreparationTime() : 30
//             ));
            
//             Orders savedOrder = orderRepository.save(order);
//             log.info("Order created successfully with ID: {}", savedOrder.getId());
            
//             return mapToResponse(savedOrder);
            
//         } catch (Exception e) {
//             log.error("Error creating order for customer: {}", userId, e);
//             throw new RuntimeException("Failed to create order: " + e.getMessage());
//         }
//     }
    
//     public List<OrderResponse> getOrdersByCustomer(Long customerId) {
//         log.info("Fetching orders for customer: {}", customerId);
        
//         List<Orders> orders = orderRepository.findByCustomerIdOrderByOrderDateDesc(customerId);
//         return orders.stream()
//                 .map(this::mapToResponse)
//                 .collect(Collectors.toList());
//     }
    
//     public OrderResponse getOrderById(Long orderId, Long customerId) {
//         log.info("Fetching order: {} for customer: {}", orderId, customerId);
        
//         Orders order = orderRepository.findById(orderId)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));
        
//         if (!order.getCustomerId().equals(customerId)) {
//             throw new RuntimeException("Order not found");
//         }
        
//         return mapToResponse(order);
//     }
    
//     @Transactional
//     public OrderResponse updateOrderStatus(Long orderId, String status) {
//         log.info("Updating order: {} status to: {}", orderId, status);
        
//         Orders order = orderRepository.findById(orderId)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));
        
//         order.setOrderStatus(OrderStatus.valueOf(status));
//         Orders updatedOrder = orderRepository.save(order);
        
//         return mapToResponse(updatedOrder);
//     }
    
//     public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
//         log.info("Fetching orders with status: {}", status);
        
//         List<Orders> orders = orderRepository.findByOrderStatus(status);
//         return orders.stream()
//                 .map(this::mapToResponse)
//                 .collect(Collectors.toList());
//     }
    
//     // ➡️ Add this method for order counts
//     public Map<String, Long> getOrderCountByStatus(Long customerId) {
//         log.info("Getting order count by status for customer: {}", customerId);
        
//         try {
//             Map<String, Long> counts = new HashMap<>();
            
//             for (OrderStatus status : OrderStatus.values()) {
//                 long count = orderRepository.countByCustomerIdAndOrderStatus(customerId, status);
//                 counts.put(status.toString(), count);
//             }
            
//             long totalCount = orderRepository.countByCustomerId(customerId);
//             counts.put("TOTAL", totalCount);
            
//             return counts;
            
//         } catch (Exception e) {
//             log.error("Error getting order counts", e);
//             throw new RuntimeException("Failed to get order counts: " + e.getMessage());
//         }
//     }
    
//     // ➡️ Comment out this method temporarily if using custom query
//     // public List<OrderResponse> getRecentOrders(Long customerId) {
//     //     log.info("Fetching recent orders for customer: {}", customerId);
//     //     
//     //     try {
//     //         LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
//     //         List<Order> orders = orderRepository.findRecentOrdersByCustomer(customerId, thirtyDaysAgo);
//     //         
//     //         return orders.stream()
//     //                 .map(this::mapToResponse)
//     //                 .collect(Collectors.toList());
//     //                 
//     //     } catch (Exception e) {
//     //         log.error("Error fetching recent orders", e);
//     //         throw new RuntimeException("Failed to fetch recent orders: " + e.getMessage());
//     //     }
//     // }
    
//     private OrderResponse mapToResponse(Orders order) {
//         OrderResponse response = new OrderResponse();
        
//         response.setId(order.getId());
//         response.setFoodId(order.getFoodId());
//         response.setFoodName(order.getFoodName());
//         response.setDescription(order.getDescription());
//         response.setPrice(order.getPrice());
//         response.setImage(order.getImage());
//         response.setCategory(order.getCategory());
//         response.setRestaurantId(order.getRestaurantId());
//         response.setRestaurantName(order.getRestaurantName());
//         response.setIsVeg(order.getIsVeg());
//         response.setRating(order.getRating());
//         response.setPreparationTime(order.getPreparationTime());
//         response.setQuantity(order.getQuantity());
//         response.setCustomerId(order.getCustomerId());
//         response.setCustomerName(order.getCustomerName());
//         response.setOrderTotal(order.getOrderTotal());
//         response.setTaxes(order.getTaxes());
//         response.setItemTotal(order.getItemTotal());
//         response.setDiscount(order.getDiscount());
//         response.setDeliveryFee(order.getDeliveryFee());
//         response.setOrderStatus(order.getOrderStatus().toString());
//         response.setPaymentStatus(order.getPaymentStatus().toString());
//         response.setOrderDate(order.getOrderDate());
//         response.setDeliveryDate(order.getDeliveryDate());
//         response.setCreatedAt(order.getCreatedAt());
        
//         return response;
//     }
// }
