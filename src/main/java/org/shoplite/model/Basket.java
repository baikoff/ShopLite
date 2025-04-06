package org.shoplite.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "baskets")
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
