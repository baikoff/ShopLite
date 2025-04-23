package org.shoplite.persistence;

import org.shoplite.model.Category;
import org.shoplite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Object> findByNameAndCategory(String name, Category category);
}
