package org.shoplite.service;

import lombok.AllArgsConstructor;
import org.shoplite.model.Product;
import org.shoplite.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Page<Product>  getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
