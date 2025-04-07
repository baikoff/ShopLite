package org.shoplite.controller;

import org.shoplite.model.Basket;
import org.shoplite.repository.BasketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {
    private final BasketRepository basketRepository;

    public BasketController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @GetMapping
    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    @PostMapping
    public Basket createBasket(@RequestBody Basket basket) {
        return basketRepository.save(basket);
    }
}
