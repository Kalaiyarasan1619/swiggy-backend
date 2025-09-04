// package com.food.repo;


// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.food.model.OrderStatus;
// import com.food.model.Orders;
// import com.stripe.model.climate.Order;

// import java.time.LocalDateTime;
// import java.util.List;

// @Repository
// public interface OrderRepository extends JpaRepository<Orders, Long> {
    
//     // ➡️ Basic methods without custom queries (these should work)
//     List<Orders> findByCustomerIdOrderByOrderDateDesc(Long customerId);
    
//     // ➡️ With pagination
//     Page<Orders> findByCustomerId(Long customerId, Pageable pageable);
    
//     // ➡️ By status
//     List<Orders> findByOrderStatus(OrderStatus orderStatus);
    
//     // ➡️ By customer and status
//     List<Orders> findByCustomerIdAndOrderStatus(Long customerId, OrderStatus orderStatus);
    
//     // ➡️ With pagination
//     Page<Orders> findByCustomerIdAndOrderStatus(Long customerId, OrderStatus orderStatus, Pageable pageable);
    
//     // ➡️ Count methods
//     long countByCustomerId(Long customerId);
//     long countByCustomerIdAndOrderStatus(Long customerId, OrderStatus orderStatus);
    
//     // ➡️ Fixed custom query with proper entity name and field names
//     @Query("SELECT o FROM Order o WHERE o.customerId = :customerId AND o.orderDate >= :fromDate ORDER BY o.orderDate DESC")
//     List<Orders> findRecentOrdersByCustomer(@Param("customerId") Long customerId, 
//                                          @Param("fromDate") LocalDateTime fromDate);
    
//     // ➡️ Alternative: Date range query
//     @Query("SELECT o FROM Order o WHERE o.customerId = :customerId AND o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
//     List<Orders> findOrdersByDateRange(@Param("customerId") Long customerId,
//                                     @Param("startDate") LocalDateTime startDate,
//                                     @Param("endDate") LocalDateTime endDate);
    
//     // ➡️ Find by restaurant
//     List<Orders> findByRestaurantIdOrderByOrderDateDesc(Long restaurantId);
// }
