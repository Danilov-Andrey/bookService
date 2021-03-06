package com.nc.bookservice.services;

import com.nc.bookservice.entities.user.Role;
import com.nc.bookservice.entities.user.User;
import com.nc.bookservice.exceptions.user.UserExistsException;
import com.nc.bookservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null){
            throw new UserExistsException("This username exists");
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setRoles(Collections.singleton(Role.USER));
        userRepo.save(newUser);
        return newUser;
    }
}
