package com.project.todo.service;

import com.project.todo.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
