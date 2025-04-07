package org.shoplite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"products", "user"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "baskets")
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    //TODO Расширить таблицу
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id") //Владелец связи так как говорит: "Я храню внешний ключ"
    private User user;

    @ManyToMany
    @JoinTable( // Владелец связи так как у неё @JoinTable
            name = "basket_product",
            joinColumns = @JoinColumn(name = "basket_id"), // Столбец для Корзин
            inverseJoinColumns = @JoinColumn(name = "product_id") // Столбец для Товара
    )
    private List<Product> products;
}
