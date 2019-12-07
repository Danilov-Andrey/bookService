package com.nc.bookservice.repos;

import com.nc.bookservice.entities.userAuth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
