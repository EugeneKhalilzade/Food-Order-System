package org.example.foodordersystem.controller;

import jakarta.validation.Valid;
import org.example.foodordersystem.model.dto.MenuItemDTO;
import org.example.foodordersystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String name) {

        List<MenuItemDTO> menuItems;

        if (category != null) {
            menuItems = menuService.getMenuItemsByCategory(category);
        } else if (minPrice != null && maxPrice != null) {
            menuItems = menuService.getMenuItemsByPriceRange(minPrice, maxPrice);
        } else if (name != null) {
            menuItems = menuService.getMenuItemsByName(name);
        } else {
            menuItems = menuService.getAllMenuItems();
        }

        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
        return menuService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO createdMenuItem = menuService.createMenuItem(menuItemDTO);
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemDTO menuItemDTO) {

        return menuService.updateMenuItem(id, menuItemDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (menuService.deleteMenuItem(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}