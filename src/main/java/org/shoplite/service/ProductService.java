package org.shoplite.service;

import lombok.AllArgsConstructor;
import org.shoplite.dto.ProductDTO;
import org.shoplite.exception.ResourceNotFoundException;
import org.shoplite.model.Category;
import org.shoplite.model.Product;
import org.shoplite.persistence.CategoryRepository;
import org.shoplite.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product createProduct(ProductDTO productDTO) {

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Категория не существует с id: " + productDTO.getCategoryId())); // Проверка существования категории

        productRepository.findByNameAndCategory(productDTO.getName(), category).ifPresent(p -> {
            throw new IllegalStateException("Продукт с именем " + productDTO.getName() + " уже есть в категории " + category.getId()); // Проверка уникальности имени продукта в категории
        });

        Product product = Product.builder()
                .withName(productDTO.getName())
                .withDescription(productDTO.getDescription())
                .withPrice(productDTO.getPrice())
                .withCategory(category)
                .build();
        return productRepository.save(product);
    }
}
