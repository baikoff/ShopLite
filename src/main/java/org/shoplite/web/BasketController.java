package org.shoplite.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shoplite.dto.BasketDTO;
import org.shoplite.model.Basket;
import org.shoplite.service.BasketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public Page<Basket> getBaskets(Pageable pageable) {
        return basketService.getBaskets(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Basket addBasket(@Valid @RequestBody BasketDTO basket) {
        return basketService.createBasket(basket);
    }
}
