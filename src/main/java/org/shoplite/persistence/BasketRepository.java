package org.shoplite.persistence;

import org.shoplite.model.Basket;
import org.shoplite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Object> findByUser(User user);
}
