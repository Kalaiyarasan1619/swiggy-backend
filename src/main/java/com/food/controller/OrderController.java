// package com.food.controller;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.food.dto.OrderRequest;
// import com.food.dto.OrderResponse;
// import com.food.model.OrderStatus; // ➡️ Make sure this exists
// import com.food.security.JwtTokenProvider; // ➡️ Make sure this exists
// import com.food.service.OrderService;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.validation.Valid;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/orders")
// @RequiredArgsConstructor
// @Slf4j
// public class OrderController {
    
//     @Autowired
//     private final OrderService orderService;
//     private final JwtTokenProvider jwtTokenProvider;
    
//     @PostMapping
//     public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, 
//                                        HttpServletRequest request) {
//         try {
//             log.info("📝 Creating order request received: {}", orderRequest.getFoodName());
            
//             // Extract user ID from JWT token
//             String token = extractTokenFromRequest(request);
//             if (token == null || !jwtTokenProvider.validateToken(token)) {
//                 log.error("❌ Invalid or missing token");
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body(createErrorResponse("Invalid or expired token"));
//             }
            
//             Long userId = jwtTokenProvider.getUserIdFromToken(token);
//             log.info("👤 Creating order for user ID: {}", userId);
            
//             OrderResponse orderResponse = orderService.createOrder(orderRequest, userId);
            
//             log.info("✅ Order created successfully with ID: {}", orderResponse.getId());
//             return ResponseEntity.status(HttpStatus.CREATED)
//                 .body(createSuccessResponse("Order created successfully", orderResponse));
                
//         } catch (Exception e) {
//             log.error("❌ Error creating order", e);
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(createErrorResponse("Failed to create order: " + e.getMessage()));
//         }
//     }
    
//     // ➡️ Enhanced GET endpoint with better response
//     @GetMapping
//     public ResponseEntity<?> getMyOrders(HttpServletRequest request) {
//         try {
//             log.info("📱 Fetching orders for current user...");
            
//             String token = extractTokenFromRequest(request);
//             if (token == null || !jwtTokenProvider.validateToken(token)) {
//                 log.error("❌ Invalid or missing token");
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body(createErrorResponse("Invalid or expired token"));
//             }
            
//             Long userId = jwtTokenProvider.getUserIdFromToken(token);
//             log.info("👤 Fetching orders for user ID: {}", userId);
            
//             List<OrderResponse> orders = orderService.getOrdersByCustomer(userId);
            
//             log.info("✅ Found {} orders for user {}", orders.size(), userId);
            
//             // ➡️ Enhanced response with additional info
//             Map<String, Object> responseData = new HashMap<>();
//             responseData.put("orders", orders);
//             responseData.put("totalOrders", orders.size());
//             responseData.put("userId", userId);
            
//             return ResponseEntity.ok(createSuccessResponse("Orders fetched successfully", responseData));
            
//         } catch (Exception e) {
//             log.error("❌ Error fetching orders", e);
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(createErrorResponse("Failed to fetch orders: " + e.getMessage()));
//         }
//     }
    
//     // ➡️ Add dedicated my-orders endpoint
//     @GetMapping("/my-orders")
//     public ResponseEntity<?> getMyOrdersDetailed(HttpServletRequest request) {
//         return getMyOrders(request); // Reuse the same logic
//     }
    
//     @GetMapping("/{orderId}")
//     public ResponseEntity<?> getOrderById(@PathVariable Long orderId, 
//                                         HttpServletRequest request) {
//         try {
//             log.info("🔍 Fetching order ID: {}", orderId);
            
//             String token = extractTokenFromRequest(request);
//             if (token == null || !jwtTokenProvider.validateToken(token)) {
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body(createErrorResponse("Invalid or expired token"));
//             }
            
//             Long userId = jwtTokenProvider.getUserIdFromToken(token);
//             log.info("👤 User {} requesting order {}", userId, orderId);
            
//             OrderResponse order = orderService.getOrderById(orderId, userId);
            
//             log.info("✅ Order {} fetched successfully", orderId);
//             return ResponseEntity.ok(createSuccessResponse("Order fetched successfully", order));
            
//         } catch (RuntimeException e) {
//             log.error("❌ Order not found: {}", orderId, e);
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                 .body(createErrorResponse("Order not found"));
//         } catch (Exception e) {
//             log.error("❌ Error fetching order by ID: {}", orderId, e);
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(createErrorResponse("Failed to fetch order"));
//         }
//     }
    
//     @PutMapping("/{orderId}/status")
//     public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
//                                              @RequestParam String status,
//                                              HttpServletRequest request) {
//         try {
//             log.info("🔄 Updating order {} status to: {}", orderId, status);
            
//             String token = extractTokenFromRequest(request);
//             if (token == null || !jwtTokenProvider.validateToken(token)) {
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body(createErrorResponse("Invalid or expired token"));
//             }
            
//             // ➡️ Validate status enum before processing
//             try {
//                 OrderStatus.valueOf(status.toUpperCase());
//             } catch (IllegalArgumentException e) {
//                 log.error("❌ Invalid status: {}", status);
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                     .body(createErrorResponse("Invalid order status: " + status));
//             }
            
//             OrderResponse updatedOrder = orderService.updateOrderStatus(orderId, status.toUpperCase());
            
//             log.info("✅ Order {} status updated to: {}", orderId, status);
//             return ResponseEntity.ok(createSuccessResponse("Order status updated successfully", updatedOrder));
            
//         } catch (Exception e) {
//             log.error("❌ Error updating order status", e);
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(createErrorResponse("Failed to update order status: " + e.getMessage()));
//         }
//     }
    
//     @GetMapping("/status/{status}")
//     public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
//         try {
//             log.info("📊 Fetching orders with status: {}", status);
            
//             // ➡️ Validate status enum
//             OrderStatus orderStatus;
//             try {
//                 orderStatus = OrderStatus.valueOf(status.toUpperCase());
//             } catch (IllegalArgumentException e) {
//                 log.error("❌ Invalid status: {}", status);
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                     .body(createErrorResponse("Invalid order status: " + status));
//             }
            
//             List<OrderResponse> orders = orderService.getOrdersByStatus(orderStatus);
            
//             // ➡️ Enhanced response
//             Map<String, Object> responseData = new HashMap<>();
//             responseData.put("orders", orders);
//             responseData.put("status", status.toUpperCase());
//             responseData.put("totalOrders", orders.size());
            
//             log.info("✅ Found {} orders with status: {}", orders.size(), status);
//             return ResponseEntity.ok(createSuccessResponse("Orders fetched successfully", responseData));
            
//         } catch (Exception e) {
//             log.error("❌ Error fetching orders by status: {}", status, e);
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(createErrorResponse("Failed to fetch orders by status"));
//         }
//     }
    
//     // ➡️ Additional endpoint: Get order counts by status
//     @GetMapping("/my-orders/count-by-status")
//     public ResponseEntity<?> getOrderCountByStatus(HttpServletRequest request) {
//         try {
//             String token = extractTokenFromRequest(request);
//             if (token == null || !jwtTokenProvider.validateToken(token)) {
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body(createErrorResponse("Invalid or expired token"));
//             }
            
//             Long userId = jwtTokenProvider.getUserIdFromToken(token);
//             log.info("📊 Getting order count by status for user: {}", userId);
            
//             Map<String, Long> counts = orderService.getOrderCountByStatus(userId);
            
//             log.info("✅ Order counts retrieved for user: {}", userId);
//             return ResponseEntity.ok(createSuccessResponse("Order counts fetched successfully", counts));
            
//         } catch (Exception e) {
//             log.error("❌ Error fetching order counts", e);
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(createErrorResponse("Failed to fetch order counts"));
//         }
//     }
    
//     // ➡️ Additional endpoint: Health check
//     @GetMapping("/health")
//     public ResponseEntity<?> healthCheck() {
//         Map<String, Object> health = new HashMap<>();
//         health.put("status", "UP");
//         health.put("service", "Order Service");
//         health.put("timestamp", System.currentTimeMillis());
        
//         return ResponseEntity.ok(createSuccessResponse("Service is healthy", health));
//     }
    
//     // Helper methods
//     private String extractTokenFromRequest(HttpServletRequest request) {
//         String bearerToken = request.getHeader("Authorization");
//         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7);
//         }
//         return null;
//     }
    
//     private Map<String, Object> createSuccessResponse(String message, Object data) {
//         Map<String, Object> response = new HashMap<>();
//         response.put("success", true);
//         response.put("message", message);
//         response.put("data", data);
//         response.put("timestamp", System.currentTimeMillis());
//         return response;
//     }
    
//     private Map<String, Object> createErrorResponse(String message) {
//         Map<String, Object> response = new HashMap<>();
//         response.put("success", false);
//         response.put("message", message);
//         response.put("timestamp", System.currentTimeMillis());
//         return response;
//     }
// }
