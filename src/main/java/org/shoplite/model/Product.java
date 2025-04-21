package org.shoplite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString(exclude = {"baskets", "category"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "products")
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    //TODO Расширить таблицу (возможно)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @ManyToMany(mappedBy = "products")
    private List<Basket> baskets;

    @ManyToOne
    @JoinColumn(name = "category_id") // Владелец связи так как говорит: "Я храню внешний ключ"
    private Category category;
}
