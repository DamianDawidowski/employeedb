package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.RoleEnum;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(String fullName) {
        User user = userRepository.findByFullName(fullName);
        return user;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User upgradeToSenior(String fullName) {
        User user = userRepository.findByFullName(fullName);
        if(user.getRoles().contains(RoleEnum.SENIOR)){
            return user;
        }
        user.addRole(RoleEnum.SENIOR);
        userRepository.save(user);
        return user;
    }

    public User upgradeToManager(String fullName) {
        User user = userRepository.findByFullName(fullName);
        if(user.getRoles().contains(RoleEnum.MANAGER)){
            return user;
        }
        user.addRole(RoleEnum.MANAGER);
        userRepository.save(user);
        return user;
    }
}