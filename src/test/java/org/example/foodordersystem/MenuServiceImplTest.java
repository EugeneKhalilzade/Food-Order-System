package org.example.foodordersystem;

import org.example.foodordersystem.model.dto.MenuItemDTO;
import org.example.foodordersystem.model.entity.MenuItem;
import org.example.foodordersystem.repository.MenuItemRepository;
import org.example.foodordersystem.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MenuServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MenuServiceImpl menuService;

    private MenuItem pizzaMenuItem;
    private MenuItem burgerMenuItem;
    private MenuItemDTO pizzaMenuItemDTO;
    private MenuItemDTO burgerMenuItemDTO;
    private List<MenuItem> menuItems;

    @BeforeEach
    void setUp() {
        pizzaMenuItem = new MenuItem();
        pizzaMenuItem.setId(1L);
        pizzaMenuItem.setName("Margherita Pizza");
        pizzaMenuItem.setDescription("Classic pizza with tomato and mozzarella");
        pizzaMenuItem.setPrice(new BigDecimal("8.99"));
        pizzaMenuItem.setCategory("Pizza");

        burgerMenuItem = new MenuItem();
        burgerMenuItem.setId(2L);
        burgerMenuItem.setName("Cheeseburger");
        burgerMenuItem.setDescription("Beef patty with cheese");
        burgerMenuItem.setPrice(new BigDecimal("6.99"));
        burgerMenuItem.setCategory("Burger");

        pizzaMenuItemDTO = new MenuItemDTO();
        pizzaMenuItemDTO.setId(1L);
        pizzaMenuItemDTO.setName("Margherita Pizza");
        pizzaMenuItemDTO.setDescription("Classic pizza with tomato and mozzarella");
        pizzaMenuItemDTO.setPrice(new BigDecimal("8.99"));
        pizzaMenuItemDTO.setCategory("Pizza");

        burgerMenuItemDTO = new MenuItemDTO();
        burgerMenuItemDTO.setId(2L);
        burgerMenuItemDTO.setName("Cheeseburger");
        burgerMenuItemDTO.setDescription("Beef patty with cheese");
        burgerMenuItemDTO.setPrice(new BigDecimal("6.99"));
        burgerMenuItemDTO.setCategory("Burger");

        menuItems = Arrays.asList(pizzaMenuItem, burgerMenuItem);

        when(modelMapper.map(pizzaMenuItem, MenuItemDTO.class)).thenReturn(pizzaMenuItemDTO);
        when(modelMapper.map(burgerMenuItem, MenuItemDTO.class)).thenReturn(burgerMenuItemDTO);
        when(modelMapper.map(pizzaMenuItemDTO, MenuItem.class)).thenReturn(pizzaMenuItem);
        when(modelMapper.map(burgerMenuItemDTO, MenuItem.class)).thenReturn(burgerMenuItem);
    }

    @Test
    void getAllMenuItems_ShouldReturnAllItems() {
        when(menuItemRepository.findAll()).thenReturn(menuItems);

        List<MenuItemDTO> result = menuService.getAllMenuItems();

        assertEquals(2, result.size());
        assertEquals(pizzaMenuItemDTO, result.get(0));
        assertEquals(burgerMenuItemDTO, result.get(1));
        verify(menuItemRepository).findAll();
    }

    @Test
    void getMenuItemsByCategory_ShouldReturnItemsByCategory() {
        when(menuItemRepository.findByCategory("Pizza")).thenReturn(List.of(pizzaMenuItem));

        List<MenuItemDTO> result = menuService.getMenuItemsByCategory("Pizza");

        assertEquals(1, result.size());
        assertEquals(pizzaMenuItemDTO, result.get(0));
        verify(menuItemRepository).findByCategory("Pizza");
    }

    @Test
    void getMenuItemsByPriceRange_ShouldReturnItemsInRange() {
        BigDecimal minPrice = new BigDecimal("5.00");
        BigDecimal maxPrice = new BigDecimal("7.00");
        when(menuItemRepository.findByPriceRange(minPrice, maxPrice)).thenReturn(List.of(burgerMenuItem));

        List<MenuItemDTO> result = menuService.getMenuItemsByPriceRange(minPrice, maxPrice);

        assertEquals(1, result.size());
        assertEquals(burgerMenuItemDTO, result.get(0));
        verify(menuItemRepository).findByPriceRange(minPrice, maxPrice);
    }

    @Test
    void getMenuItemsByName_ShouldReturnItemsContainingName() {
        when(menuItemRepository.findByNameContainingIgnoreCase("pizza")).thenReturn(List.of(pizzaMenuItem));

        List<MenuItemDTO> result = menuService.getMenuItemsByName("pizza");

        assertEquals(1, result.size());
        assertEquals(pizzaMenuItemDTO, result.get(0));
        verify(menuItemRepository).findByNameContainingIgnoreCase("pizza");
    }

    @Test
    void getMenuItemById_WhenItemExists_ShouldReturnItem() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(pizzaMenuItem));

        Optional<MenuItemDTO> result = menuService.getMenuItemById(1L);

        assertTrue(result.isPresent());
        assertEquals(pizzaMenuItemDTO, result.get());
        verify(menuItemRepository).findById(1L);
    }

    @Test
    void getMenuItemById_WhenItemDoesNotExist_ShouldReturnEmpty() {
        when(menuItemRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<MenuItemDTO> result = menuService.getMenuItemById(999L);

        assertFalse(result.isPresent());
        verify(menuItemRepository).findById(999L);
    }

    @Test
    void createMenuItem_ShouldSaveAndReturnItem() {
        when(menuItemRepository.save(pizzaMenuItem)).thenReturn(pizzaMenuItem);

        MenuItemDTO result = menuService.createMenuItem(pizzaMenuItemDTO);

        assertNotNull(result);
        assertEquals(pizzaMenuItemDTO, result);
        verify(menuItemRepository).save(pizzaMenuItem);
    }

    @Test
    void updateMenuItem_WhenItemExists_ShouldUpdateAndReturnItem() {
        MenuItemDTO updatedDTO = new MenuItemDTO();
        updatedDTO.setId(1L);
        updatedDTO.setName("Updated Pizza");
        updatedDTO.setPrice(new BigDecimal("9.99"));

        MenuItem existingItem = new MenuItem();
        existingItem.setId(1L);
        existingItem.setName("Margherita Pizza");

        MenuItem updatedItem = new MenuItem();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Pizza");
        updatedItem.setPrice(new BigDecimal("9.99"));

        MenuItemDTO expectedDTO = new MenuItemDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Updated Pizza");
        expectedDTO.setPrice(new BigDecimal("9.99"));

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(menuItemRepository.save(existingItem)).thenReturn(updatedItem);
        when(modelMapper.map(updatedItem, MenuItemDTO.class)).thenReturn(expectedDTO);
        doNothing().when(modelMapper).map(updatedDTO, existingItem);

        Optional<MenuItemDTO> result = menuService.updateMenuItem(1L, updatedDTO);

        assertTrue(result.isPresent());
        assertEquals(expectedDTO, result.get());
        verify(menuItemRepository).findById(1L);
        verify(menuItemRepository).save(existingItem);
        verify(modelMapper).map(updatedDTO, existingItem);
    }

    @Test
    void updateMenuItem_WhenItemDoesNotExist_ShouldReturnEmpty() {
        when(menuItemRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<MenuItemDTO> result = menuService.updateMenuItem(999L, new MenuItemDTO());

        assertFalse(result.isPresent());
        verify(menuItemRepository).findById(999L);
        verify(menuItemRepository, never()).save(any());
    }

    @Test
    void deleteMenuItem_WhenItemExists_ShouldReturnTrue() {
        when(menuItemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(menuItemRepository).deleteById(1L);

        boolean result = menuService.deleteMenuItem(1L);

        assertTrue(result);
        verify(menuItemRepository).existsById(1L);
        verify(menuItemRepository).deleteById(1L);
    }

    @Test
    void deleteMenuItem_WhenItemDoesNotExist_ShouldReturnFalse() {
        when(menuItemRepository.existsById(999L)).thenReturn(false);

        boolean result = menuService.deleteMenuItem(999L);

        assertFalse(result);
        verify(menuItemRepository).existsById(999L);
        verify(menuItemRepository, never()).deleteById(any());
    }
}