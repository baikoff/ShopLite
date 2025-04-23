package org.shoplite.service;

import lombok.AllArgsConstructor;
import org.shoplite.dto.UserDTO;
import org.shoplite.model.User;
import org.shoplite.persistence.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).ifPresent(u -> {
            throw new IllegalStateException("Email уже занят: " + userDTO.getEmail()); // Проверка уникальности email
        });

        User user = User.builder()
                .withName(userDTO.getName())
                .withEmail(userDTO.getEmail())
                .build();
        return userRepository.save(user);
    }
}
