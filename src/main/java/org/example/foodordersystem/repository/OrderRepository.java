package org.example.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderRepository, Long> {
    List<OrderRepository> findByUserId(Long userId);

    List<OrderRepository> findByStatus(String status);

    List<OrderRepository> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<OrderRepository> findByUserIdAndStatus(Long userId, String status);
}