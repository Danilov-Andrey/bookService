package com.nc.bookservice.services;

import com.nc.bookservice.entities.user.Role;
import com.nc.bookservice.entities.user.User;
import com.nc.bookservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    private UserRepo userRepo;

    @Autowired
    public AuthService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setRoles(Collections.singleton(Role.USER));
        userRepo.save(newUser);
        return newUser;
    }
}
