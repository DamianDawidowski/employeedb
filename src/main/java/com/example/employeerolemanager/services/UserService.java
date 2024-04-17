package com.example.employeerolemanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeerolemanager.entities.RoleEnum;
import com.example.employeerolemanager.entities.User;
import com.example.employeerolemanager.repositories.UserRepository;

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