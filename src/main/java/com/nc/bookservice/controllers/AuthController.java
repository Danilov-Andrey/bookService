package com.nc.bookservice.controllers;

import com.nc.bookservice.entities.user.User;
import com.nc.bookservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/validateUser")
    public Principal validateUser(Principal user) {
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
       return authService.createUser(user);
    }
}
