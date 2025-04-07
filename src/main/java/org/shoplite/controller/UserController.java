package org.shoplite.controller;

import org.shoplite.model.User;
import org.shoplite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/newUser")
    public User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }
}
