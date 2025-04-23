package org.shoplite.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.shoplite.dto.BasketDTO;
import org.shoplite.exception.ResourceNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback // Наверняка есть более элегантное решение этой проблемы (данные захламляют БД)
class BasketServiceTest {
    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testGetAllBasketsWithPagination() {

        basketRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        // Создаём данные
        Category category = Category.builder()
                .withName("Электроника")
                .build();
        categoryRepository.save(category);

        User user = User.builder()
                .withName("Иван")
                .withEmail("ivan@example.com")
                .build();
        userRepository.save(user);

        Product product = Product.builder()
                .withName("Телефон")
                .withDescription("Смартфон")
                .withPrice(new BigDecimal("999.99"))
                .withCategory(category)
                .build();
        productRepository.save(product);

        Basket basket = Basket.builder()
                .withUser(user)
                .withProducts(new ArrayList<>())
                .build();
        basket.getProducts().add(product);
        basketRepository.save(basket);

        // Проверяем пагинацию
        Pageable pageable = PageRequest.of(0, 2);
        Page<Basket> page = basketService.getBaskets(pageable);

        assertEquals(1, page.getTotalElements(), "Должна быть 1 корзина");
    }

    @Test
    void testCreateBasketWithNonExistentUser() {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setUserId(999L);
        basketDTO.setProductIds(List.of(1L));

        assertThrows(ResourceNotFoundException.class, () -> basketService.createBasket(basketDTO));
    }

    @Test
    void testCreateBasketWithDuplicateProductIds() {
        User user = User.builder()
                .withName("Иван")
                .withEmail("ivan@example.com")
                .build();
        userRepository.save(user);

        Category category = Category.builder().withName("Электроника").build();
        categoryRepository.save(category);

        Product product = Product.builder()
                .withName("Телефон")
                .withDescription("Смартфон")
                .withPrice(new BigDecimal("999.99"))
                .withCategory(category)
                .build();
        productRepository.save(product);

        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setUserId(user.getId());
        basketDTO.setProductIds(List.of(product.getId(), product.getId()));

        assertThrows(IllegalArgumentException.class, () -> basketService.createBasket(basketDTO));
    }

    @Test
    void testCreateBasketWhenBasketAlreadyExists() {
        User user = User.builder()
                .withName("Иван")
                .withEmail("ivan@example.com")
                .build();
        userRepository.save(user);

        Category category = Category.builder().withName("Электроника").build();
        categoryRepository.save(category);

        Product product = Product.builder()
                .withName("Телефон")
                .withDescription("Смартфон")
                .withPrice(new BigDecimal("999.99"))
                .withCategory(category)
                .build();
        productRepository.save(product);

        // Создаём первую корзину
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setUserId(user.getId());
        basketDTO.setProductIds(List.of(product.getId()));
        basketService.createBasket(basketDTO);

        // Пытаемся создать вторую
        assertThrows(IllegalStateException.class, () -> basketService.createBasket(basketDTO));
    }
}