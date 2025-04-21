package org.shoplite.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.shoplite.model.Category;
import org.shoplite.persistence.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testGetCategoriesWithPagination() {
        // Очищаем базу
        categoryRepository.deleteAll(); // найти вариант без хард зачистки

        // Создаём категории
        Category category1 = Category.builder().withName("Электроника").build();
        Category category2 = Category.builder().withName("Одежда").build();
        Category category3 = Category.builder().withName("Книги").build();
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // Проверяем пагинацию
        Pageable pageable = PageRequest.of(0, 2);
        Page<Category> page = categoryService.getCategories(pageable);

        assertEquals(3, page.getTotalElements(), "Должно быть 3 категории");
        assertEquals(2, page.getContent().size(), "На странице должно быть 2 категории");
        assertEquals(0, page.getNumber(), "Должна быть страница 0");
        assertEquals(2, page.getSize(), "Размер страницы должен быть 2");
        assertEquals(2, page.getTotalPages(), "Должно быть 2 страницы");
    }
}