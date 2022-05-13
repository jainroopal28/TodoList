package com.project.todo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.todo.model.Role;
import com.project.todo.model.User;
import com.project.todo.repository.RoleRepository;
import com.project.todo.repository.UserRepository;
import com.project.todo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
    	logger.debug("saving user");
        
        ArrayList<Role> roleList = new ArrayList<Role>();
        roleList.add(roleRepository.findOneByName("USER"));
        
        user.setRoles(new HashSet<>(roleList));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
        	userRepository.save(user);
        }
        catch (ConstraintViolationException ex) {
        	logger.error("username already exists");
        }
    }

    @Override
    public User findByUsername(String username) {
    	logger.debug("finding user by username");

        return userRepository.findByUsername(username);
    }
}
