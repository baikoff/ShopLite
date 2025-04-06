package org.shoplite.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "products")
    private List<Basket> baskets;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
