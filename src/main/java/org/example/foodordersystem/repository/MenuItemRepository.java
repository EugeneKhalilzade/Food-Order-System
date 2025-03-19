package org.example.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemRepository, Long> {
    List<MenuItemRepository> findByCategory(String category);

    @Query("SELECT m FROM MenuItem m WHERE m.price BETWEEN :minPrice AND :maxPrice")
    List<MenuItemRepository> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<MenuItemRepository> findByCategoryAndPriceLessThanEqual(String category, BigDecimal maxPrice);

    List<MenuItemRepository> findByNameContainingIgnoreCase(String name);
}