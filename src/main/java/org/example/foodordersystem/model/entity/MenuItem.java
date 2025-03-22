package org.example.foodordersystem.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "menu_items")
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "menuItem")
    private List<OrderItem> orderItems;


    public MenuItem(Long id, @NotBlank(message = "Name is required") String name, String description,
                    @NotNull(message = "Price is required")
                    @Positive(message = "Price must be positive") BigDecimal price,
                    @NotBlank(message = "Category is required") String category) {
    }
}