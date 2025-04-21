package org.shoplite.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.shoplite.model.User;
import org.shoplite.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUsersWithPagination() {
        // Очищаем базу
        userRepository.deleteAll();

        // Создаём пользователей
        User user1 = User.builder()
                .withName("Иван")
                .withEmail("ivan@example.com")
                .build();
        User user2 = User.builder()
                .withName("Мария")
                .withEmail("maria@example.com")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        // Проверяем пагинацию
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> page = userService.getUsers(pageable);

        assertEquals(2, page.getTotalElements(), "Должно быть 2 пользователя");
        assertEquals(1, page.getContent().size(), "На странице должен быть 1 пользователь");
        assertEquals(0, page.getNumber(), "Должна быть страница 0");
        assertEquals(1, page.getSize(), "Размер страницы должен быть 1");
        assertEquals(2, page.getTotalPages(), "Должно быть 2 страницы");
    }
}