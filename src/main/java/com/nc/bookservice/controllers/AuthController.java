package com.nc.bookservice.controllers;

import com.nc.bookservice.entities.userAuth.Message;
import com.nc.bookservice.entities.userAuth.Role;
import com.nc.bookservice.entities.userAuth.User;
import com.nc.bookservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/validateLogin")
    public Message validateLogin() {
        return new Message("User successfully authenticated");
    }

    @PostMapping("/create")
    public User user(@RequestBody User user) {
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setRoles(Collections.singleton(Role.USER));
        userRepo.save(newUser);

        return user;
    }
}
