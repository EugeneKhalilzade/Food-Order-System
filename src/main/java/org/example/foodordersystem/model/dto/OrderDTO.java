package org.example.foodordersystem.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    private Long userId;

    private LocalDateTime orderDate;

    private String status;

    private BigDecimal totalAmount;

    @NotEmpty(message = "Order must contain at least one item")
    private List<@Valid OrderItemDTO> orderItems;
}