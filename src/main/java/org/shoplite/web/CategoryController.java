package org.shoplite.web;

import lombok.AllArgsConstructor;
import org.shoplite.model.Category;
import org.shoplite.persistence.CategoryRepository;
import org.shoplite.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<Category> getCategories(Pageable pageable) {
        return categoryService.getCategories(pageable);
    }

//    @PostMapping
//    public Category addCategory(@RequestBody Category category) {
//        return categoryRepository.save(category);
//    }
}
