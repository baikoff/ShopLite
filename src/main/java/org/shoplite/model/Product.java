package org.shoplite.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    //TODO Расширить таблицу (возможно)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "products")
    private List<Basket> baskets;

    @ManyToOne
    @JoinColumn(name = "category_id") // Владелец связи так как говорит: "Я храню внешний ключ"
    private Category category;
}
