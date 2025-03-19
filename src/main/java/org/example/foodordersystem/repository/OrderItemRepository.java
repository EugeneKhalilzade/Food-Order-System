package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemRepository, Long> {
    List<OrderItemRepository> findByOrder(Order order);

    List<OrderItemRepository> findByMenuItemId(Long menuItemId);
}