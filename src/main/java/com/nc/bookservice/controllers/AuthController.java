package com.nc.bookservice.controllers;

import com.nc.bookservice.entities.user.User;
import com.nc.bookservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {
            User user = authService.createUser(newUser);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
