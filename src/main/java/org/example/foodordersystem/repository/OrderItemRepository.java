package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.Order;
import org.example.foodordersystem.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//burada deyisiklik var
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}