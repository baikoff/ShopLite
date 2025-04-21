package org.shoplite.web;

import lombok.RequiredArgsConstructor;
import org.shoplite.model.User;
import org.shoplite.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public Page<User> allUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

//    @PostMapping("/newUser")
//    public User newUser(@RequestBody User newUser) {
//        return userRepository.save(newUser);
//    }
}
