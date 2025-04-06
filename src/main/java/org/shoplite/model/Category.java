package org.shoplite.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    //TODO Расширить таблицу (Возможно)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category") // Я не владелец, связь управляется category в Product
    private List<Product> products;
}
