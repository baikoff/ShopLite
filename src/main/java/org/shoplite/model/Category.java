package org.shoplite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"products"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "categories")
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    //TODO Расширить таблицу (Возможно)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category") // Я не владелец, связь управляется category в Product
    private List<Product> products;
}
