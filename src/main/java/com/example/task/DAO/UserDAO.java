package com.example.task.DAO;

import com.example.task.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
