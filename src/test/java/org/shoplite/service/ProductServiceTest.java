package org.shoplite.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.shoplite.dto.ProductDTO;
import org.shoplite.exception.ResourceNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testCreateProduct() {
        // Очищаем базу
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        // Создаём категорию
        Category category = Category.builder().withName("Электроника").build();
        categoryRepository.save(category);

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Телефон");
        productDTO.setDescription("Смартфон");
        productDTO.setPrice(new BigDecimal("999.99"));
        productDTO.setCategoryId(category.getId());
        Product product = productService.createProduct(productDTO);

        // Проверяем
        assertNotNull(product.getId());
        assertEquals("Телефон", product.getName());
        assertEquals("Смартфон", product.getDescription());
        assertEquals(new BigDecimal("999.99"), product.getPrice());
        assertEquals(category.getId(), product.getCategory().getId());
    }

    @Test
    void testCreateProductWithNonExistentCategory() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Телефон");
        productDTO.setDescription("Смартфон");
        productDTO.setPrice(new BigDecimal("999.99"));
        productDTO.setCategoryId(999L);

        assertThrows(ResourceNotFoundException.class, () -> productService.createProduct(productDTO));
    }

    @Test
    void testCreateProductWithDuplicateNameInCategory() {
        // Создаём категорию
        Category category = Category.builder().withName("Электроника").build();
        categoryRepository.save(category);

        // Создаём первый продукт
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setName("Телефон");
        productDTO1.setDescription("Смартфон");
        productDTO1.setPrice(new BigDecimal("999.99"));
        productDTO1.setCategoryId(category.getId());
        productService.createProduct(productDTO1);

        // Пытаемся создать второй с тем же именем в той же категории
        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setName("Телефон");
        productDTO2.setDescription("Другой смартфон");
        productDTO2.setPrice(new BigDecimal("899.99"));
        productDTO2.setCategoryId(category.getId());

        assertThrows(IllegalStateException.class, () -> productService.createProduct(productDTO2));
    }
}