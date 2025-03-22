package org.example.foodordersystem.service.impl;

import org.example.foodordersystem.model.dto.MenuItemDTO;
import org.example.foodordersystem.model.entity.MenuItem;
import org.example.foodordersystem.repository.MenuItemRepository;
import org.example.foodordersystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category)
                .stream()
                .map((MenuItemRepository menuItem) -> convertToDTO((MenuItem) menuItem))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return menuItemRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map((MenuItemRepository menuItem) -> convertToDTO((MenuItem) menuItem))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByName(String name) {
        return menuItemRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map((MenuItemRepository menuItem) -> convertToDTO((MenuItem) menuItem))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MenuItemDTO> getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = convertToEntity(menuItemDTO);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return convertToDTO(savedMenuItem);
    }

    @Override
    public Optional<MenuItemDTO> updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        return menuItemRepository.findById(id)
                .map(existingItem -> {
                    MenuItem updatedItem = convertToEntity(menuItemDTO);
                    updatedItem.setId(id);
                    return menuItemRepository.save(updatedItem);
                })
                .map(this::convertToDTO);
    }

    @Override
    public boolean deleteMenuItem(Long id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MenuItemDTO convertToDTO(MenuItem menuItem) {
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getCategory()
        );
    }

    private MenuItem convertToEntity(MenuItemDTO dto) {
        return new MenuItem(
                dto.getId(), // Yalnız `updateMenuItem` metodu üçün dolacaq
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getCategory()
        );
    }
}
