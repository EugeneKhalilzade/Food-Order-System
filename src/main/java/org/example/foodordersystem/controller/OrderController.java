package org.example.foodordersystem.controller;

import jakarta.validation.Valid;
import org.example.foodordersystem.model.dto.OrderDTO;
import org.example.foodordersystem.model.dto.OrderStatusDTO;
import org.example.foodordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String status) {

        List<OrderDTO> orders;

        if (userId != null && status != null) {
            orders = orderService.getOrdersByUserIdAndStatus(userId, status);
        } else if (userId != null) {
            orders = orderService.getOrdersByUserId(userId);
        } else if (status != null) {
            orders = orderService.getOrdersByStatus(status);
        } else {
            orders = orderService.getAllOrders();
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            @RequestParam Long userId) {

        OrderDTO createdOrder = orderService.createOrder(orderDTO, userId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusDTO statusDTO) {

        return orderService.updateOrderStatus(id, statusDTO.getStatus())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        List<OrderDTO> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }
}