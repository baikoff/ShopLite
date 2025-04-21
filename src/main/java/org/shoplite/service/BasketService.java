package org.shoplite.service;

import lombok.AllArgsConstructor;
import org.shoplite.model.Basket;
import org.shoplite.persistence.BasketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    public Page<Basket> getBaskets(Pageable pageable) {
        return basketRepository.findAll(pageable);
    }
}
