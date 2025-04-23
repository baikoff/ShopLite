package org.shoplite.service;

import lombok.AllArgsConstructor;
import org.shoplite.dto.BasketDTO;
import org.shoplite.exception.ResourceNotFoundException;
import org.shoplite.model.Basket;
import org.shoplite.model.Product;
import org.shoplite.model.User;
import org.shoplite.persistence.BasketRepository;
import org.shoplite.persistence.ProductRepository;
import org.shoplite.persistence.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public Page<Basket> getBaskets(Pageable pageable) {
        return basketRepository.findAll(pageable);
    }

    public Basket createBasket(BasketDTO basketDTO) {
        if (new HashSet<>(basketDTO.getProductIds()).size() != basketDTO.getProductIds().size()) { // Проверка дубликатов productIds
            throw new IllegalArgumentException("Duplicate product IDs are not allowed");
        }

        User user = userRepository.findById(basketDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User с ид: " + basketDTO.getUserId() + " не найден")); // Проверка существования пользователя

        List<Product> products = productRepository.findAllById(basketDTO.getProductIds()); // Проверка существования продуктов

        if (products.size() != basketDTO.getProductIds().size()) {
            throw new ResourceNotFoundException("Такого продукта нет");
        }

        basketRepository.findByUser(user).ifPresent(b -> {
            throw new IllegalStateException("Корзина для user с id: " + user.getId() + " пуста"); // Проверка уникальности корзины
        });

        Basket basket = Basket.builder()
                .withUser(user)
                .withProducts(products)
                .build();
        return basketRepository.save(basket);
    }
}
