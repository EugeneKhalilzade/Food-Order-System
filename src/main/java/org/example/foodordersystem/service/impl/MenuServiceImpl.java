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
        return menuItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return menuItemRepository.findByPriceRange(minPrice, maxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByName(String name) {
        return menuItemRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
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
        if (!menuItemRepository.existsById(id)) {
            return Optional.empty();
        }

        MenuItem menuItem = convertToEntity(menuItemDTO);
        menuItem.setId(id);
        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
        return Optional.of(convertToDTO(updatedMenuItem));
    }

    @Override
    public boolean deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            return false;
        }

        menuItemRepository.deleteById(id);
        return true;
    }

    private MenuItemDTO convertToDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setCategory(menuItem.getCategory());
        return dto;
    }

    private MenuItem convertToEntity(MenuItemDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        return menuItem;
    }
}