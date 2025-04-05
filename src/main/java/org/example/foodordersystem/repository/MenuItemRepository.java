package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategory(String category);  // Changed from List<MenuItemRepository>

    @Query("SELECT m FROM MenuItem m WHERE m.price BETWEEN :minPrice AND :maxPrice")
    List<MenuItem> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);  // Changed from List<MenuItemRepository>

    List<MenuItem> findByCategoryAndPriceLessThanEqual(String category, BigDecimal maxPrice);  // Changed from List<MenuItemRepository>

    List<MenuItem> findByNameContainingIgnoreCase(String name);  // Changed from List<MenuItemRepository>
}