package org.example.foodordersystem.service.impl;

import org.example.foodordersystem.model.dto.MenuItemDTO;
import org.example.foodordersystem.model.entity.MenuItem;
import org.example.foodordersystem.repository.MenuItemRepository;
import org.example.foodordersystem.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    public MenuServiceImpl(MenuItemRepository menuItemRepository, ModelMapper modelMapper) {
        this.menuItemRepository = menuItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll()
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category)
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return menuItemRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByName(String name) {
        return menuItemRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MenuItemDTO> getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class));
    }

    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = modelMapper.map(menuItemDTO, MenuItem.class);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return modelMapper.map(savedMenuItem, MenuItemDTO.class);
    }

    @Override
    public Optional<MenuItemDTO> updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        return menuItemRepository.findById(id)
                .map(existingItem -> {
                    // Map the DTO to the existing entity
                    modelMapper.map(menuItemDTO, existingItem);
                    return menuItemRepository.save(existingItem);
                })
                .map(updatedItem -> modelMapper.map(updatedItem, MenuItemDTO.class));
    }

    @Override
    public boolean deleteMenuItem(Long id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}