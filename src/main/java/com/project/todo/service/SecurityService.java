package com.project.todo.service;

import org.springframework.cache.annotation.Cacheable;

public interface SecurityService {
    String findLoggedInUsername();
	@Cacheable("autologin")
    void autologin(String username, String password);
}
