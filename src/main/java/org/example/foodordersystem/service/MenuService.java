package org.example.foodordersystem.service;

import org.example.foodordersystem.model.dto.MenuItemDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<MenuItemDTO> getAllMenuItems();
    List<MenuItemDTO> getMenuItemsByCategory(String category);
    List<MenuItemDTO> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<MenuItemDTO> getMenuItemsByName(String name);
    Optional<MenuItemDTO> getMenuItemById(Long id);
    MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO);
    Optional<MenuItemDTO> updateMenuItem(Long id, MenuItemDTO menuItemDTO);
    boolean deleteMenuItem(Long id);
}