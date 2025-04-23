package org.shoplite.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shoplite.dto.UserDTO;
import org.shoplite.model.User;
import org.shoplite.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public Page<User> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
}
