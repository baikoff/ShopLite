package org.shoplite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@ToString(exclude = {"basket"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //TODO Расширить таблицу
    //TODO Создать Файлик для разного и умного
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToOne(mappedBy = "user") //Я не владелец, связь управляется полем user в Basket
    private Basket basket;
}

//Hibernate использует владельца, чтобы знать, где обновлять данные. Например, если ты добавишь Product в Basket, Hibernate обновит basket_product