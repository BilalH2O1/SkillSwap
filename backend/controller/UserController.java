package com.skillswap.backend.controller;

import com.skillswap.backend.model.User;
import com.skillswap.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 2. Register a new user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // 3. Get user by ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 4. THE ATM (Deposit Money)
    // URL: POST http://localhost:8080/users/6/deposit?amount=500
    @PostMapping("/{id}/deposit")
    public User depositMoney(@PathVariable Long id, @RequestParam Double amount) {
        return userService.addMoney(id, amount);
    }
}