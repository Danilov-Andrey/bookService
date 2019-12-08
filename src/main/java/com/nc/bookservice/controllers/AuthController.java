package com.nc.bookservice.controllers;

import com.nc.bookservice.entities.user.Message;
import com.nc.bookservice.entities.user.User;
import com.nc.bookservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/validateLogin")
    public Message validateLogin() {
        return new Message("User successfully authenticated");
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
       return authService.createUser(user);
    }
}
