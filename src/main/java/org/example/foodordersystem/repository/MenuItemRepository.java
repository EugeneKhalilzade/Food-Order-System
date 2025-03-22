package org.example.foodordersystem.repository;

import org.example.foodordersystem.model.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
//burada deyisiklik var
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItemRepository> findByCategory(String category);

    @Query("SELECT m FROM MenuItem m WHERE m.price BETWEEN :minPrice AND :maxPrice")
    List<MenuItemRepository> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<MenuItemRepository> findByCategoryAndPriceLessThanEqual(String category, BigDecimal maxPrice);

    List<MenuItemRepository> findByNameContainingIgnoreCase(String name);


}