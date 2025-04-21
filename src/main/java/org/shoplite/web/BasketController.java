package org.shoplite.web;

import lombok.RequiredArgsConstructor;
import org.shoplite.model.Basket;
import org.shoplite.service.BasketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public Page<Basket> getBaskets(Pageable pageable) {
        return basketService.getBaskets(pageable);
    }

//    @PostMapping
//    public Basket createBasket(@RequestBody Basket basket) {
//        return basketService.save(basket);
//    }
}
