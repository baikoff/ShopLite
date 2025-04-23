package org.shoplite.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.shoplite.dto.CategoryDTO;
import org.shoplite.model.Category;
import org.shoplite.persistence.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category createCategory(CategoryDTO categoryDTO) {

        categoryRepository.findByName(categoryDTO.getName()).ifPresent(c -> {
            throw new IllegalStateException("Категория с именем " + categoryDTO.getName() + " уже существует"); // Проверка уникальности имени категории
        });

        Category category = Category.builder()
                .withName(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }
}
