package org.shoplite;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.shoplite.model.Basket;
import org.shoplite.model.Category;
import org.shoplite.model.Product;
import org.shoplite.model.User;
import org.shoplite.persistence.BasketRepository;
import org.shoplite.persistence.CategoryRepository;
import org.shoplite.persistence.ProductRepository;
import org.shoplite.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;

@SpringBootTest
public class ShopLiteTest {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    void testDatabase() {
        // Создание категории
        Category category = Category.builder()
                .withName("Электроника")
                .build();
        categoryRepository.save(category);

        // Создание продукта и привязка категории
        Product product = Product.builder()
                .withName("Телефон")
                .withDescription("Смартфон")
                .withCategory(category)
                .build();
        productRepository.save(product);

        // Создание пользователя
        User user = User.builder()
                .withName("Иван")
                .withEmail("ivan@example.com")
                .build();
        userRepository.save(user);

        // Создание корзины, привязка пользователя и добавление продукта
        Basket basket = Basket.builder()
                .withUser(user)
                .withProducts(new ArrayList<>())
                .build();
        basket.getProducts().add(product);
        basketRepository.save(basket);

        // Проверка результата
        System.out.println("Пользователи: " + userRepository.findAll());
        System.out.println("Корзины: " + basketRepository.findAll());
        System.out.println("Продукты: " + productRepository.findAll());
        System.out.println("Категории: " + categoryRepository.findAll());
    }
}
