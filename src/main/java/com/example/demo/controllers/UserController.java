package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.entities.BasicUser;
import com.example.demo.entities.RoleEnum;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;

import jakarta.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    } 
 
    @GetMapping("/{fullName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicUser> foundUser(@PathVariable String fullName) {
 
        User foundUser = userService.findUser(fullName);
        BasicUser userInfo= new BasicUser();
        userInfo.setEmail(foundUser.getEmail());
        userInfo.setFullName(foundUser.getFullName());
        userInfo.setRoles(foundUser.getRoles());

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/makeSenior/{fullName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<User> upgradeToSenior(@PathVariable String fullName) {
        return ResponseEntity.ok(userService.upgradeToSenior(fullName));
    }

    @PutMapping("/makeManager/{fullName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> upgradeToManager(@PathVariable String fullName) {
        return ResponseEntity.ok(userService.upgradeToManager(fullName));
    }

     
}