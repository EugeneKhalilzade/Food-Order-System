package org.example.foodordersystem.service;

import org.example.foodordersystem.model.dto.OrderDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getOrdersByUserId(Long userId);
    List<OrderDTO> getOrdersByStatus(String status);
    List<OrderDTO> getOrdersByUserIdAndStatus(Long userId, String status);
    List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Optional<OrderDTO> getOrderById(Long id);
    OrderDTO createOrder(OrderDTO orderDTO, Long userId);
    Optional<OrderDTO> updateOrderStatus(Long id, String status);
}