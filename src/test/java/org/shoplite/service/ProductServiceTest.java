package org.shoplite.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.shoplite.model.Category;
import org.shoplite.model.Product;
import org.shoplite.persistence.CategoryRepository;
import org.shoplite.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testGetProductsWithPagination() {
        // Очищаем базу
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        // Создаём категорию
        Category category = Category.builder().withName("Электроника").build();
        categoryRepository.save(category);

        // Создаём продукты
        Product product1 = Product.builder()
                .withName("Телефон")
                .withDescription("Смартфон")
                .withPrice(new BigDecimal("999.99"))
                .withCategory(category)
                .build();
        Product product2 = Product.builder()
                .withName("Ноутбук")
                .withDescription("Мощный ноут")
                .withPrice(new BigDecimal("1999.99"))
                .withCategory(category)
                .build();
        productRepository.save(product1);
        productRepository.save(product2);

        // Проверяем пагинацию
        Pageable pageable = PageRequest.of(0, 1);
        Page<Product> page = productService.getProducts(pageable);

        assertEquals(2, page.getTotalElements(), "Должно быть 2 продукта");
        assertEquals(1, page.getContent().size(), "На странице должен быть 1 продукт");
        assertEquals(0, page.getNumber(), "Должна быть страница 0");
        assertEquals(1, page.getSize(), "Размер страницы должен быть 1");
        assertEquals(2, page.getTotalPages(), "Должно быть 2 страницы");
    }
}