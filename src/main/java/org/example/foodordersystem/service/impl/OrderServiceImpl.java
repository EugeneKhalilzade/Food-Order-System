package org.example.foodordersystem.service.impl;

import org.example.foodordersystem.model.dto.OrderDTO;
import org.example.foodordersystem.model.dto.OrderItemDTO;
import org.example.foodordersystem.model.entity.MenuItem;
import org.example.foodordersystem.model.entity.Order;
import org.example.foodordersystem.model.entity.OrderItem;
import org.example.foodordersystem.model.entity.User;
import org.example.foodordersystem.repository.MenuItemRepository;
import org.example.foodordersystem.repository.OrderItemRepository;
import org.example.foodordersystem.repository.OrderRepository;
import org.example.foodordersystem.repository.UserRepository;
import org.example.foodordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//Bu classda deyisiklikler var
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            UserRepository userRepository,
                            MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> collect = orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map((OrderRepository order) -> convertToDTO((Order) order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status).stream()
                .map((OrderRepository order) -> convertToDTO((Order) order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByUserIdAndStatus(Long userId, String status) {
        return orderRepository.findByUserIdAndStatus(userId, status).stream()
                .map((OrderRepository order) -> convertToDTO((Order) order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map((OrderRepository order) -> convertToDTO((Order) order))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);  // Correcting the map function
    }


    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // Calculate total amount
        BigDecimal totalAmount = orderDTO.getOrderItems().stream()
                .map(itemDTO -> itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(itemDTO -> {
            MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemDTO.getMenuItemId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(itemDTO.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        return convertToDTO(savedOrder);
    }

    @Override
    @Transactional
    public Optional<OrderDTO> updateOrderStatus(Long id, String status) {
        Optional<Order> orderOpt = orderRepository.findById(id); // Düzəldilmişdir

        if (orderOpt.isEmpty()) {
            return Optional.empty();
        }

        Order order = orderOpt.get(); // Cast etməyə ehtiyac yoxdur
        order.setStatus(status);

        return Optional.of(convertToDTO(orderRepository.save(order)));
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>(orderItems.size());  // Optimize ArrayList size

        // Using for-each loop for clarity and easy debugging
        for (OrderItem orderItem : orderItems) {
            orderItemDTOs.add(convertOrderItemToDTO(orderItem));  // Clearer method name for item conversion
        }

        dto.setOrderItems(orderItemDTOs);

        return dto;
    }

    private OrderItemDTO convertOrderItemToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setMenuItemId(orderItem.getMenuItem().getId());
        dto.setMenuItemName(orderItem.getMenuItem().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        return dto;
    }


}
