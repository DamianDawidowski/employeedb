package com.example.employeerolemanager.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

import com.example.employeerolemanager.entities.BasicUser; 
import com.example.employeerolemanager.entities.User;
import com.example.employeerolemanager.services.UserService; 


@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    } 
 
    @GetMapping("/{fullName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicUser> foundUser(@PathVariable String fullName, Authentication authentication) {
 
        User foundUser = userService.findUser(fullName);

        BasicUser userInfo= new BasicUser();
        userInfo.setEmail(foundUser.getEmail());
        userInfo.setFullName(foundUser.getFullName());
        userInfo.setRoles(foundUser.getRoles());

        LOGGER.info("User {} data have been accessed by user {}", fullName, authentication.getName()); 

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<User>> allUsers(Authentication authentication) { 

        LOGGER.info("All user data have been accessed by user {}", authentication.getName()); 
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/makeSenior/{fullName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<User> upgradeToSenior(@PathVariable String fullName, Authentication authentication) {
        String username = authentication.getName(); 

        LOGGER.info("User {} has been granted a role of a SENIOR by user {}", fullName, username); 

        return ResponseEntity.ok(userService.upgradeToSenior(fullName));
    }

    @PutMapping("/makeManager/{fullName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> upgradeToManager(@PathVariable String fullName, Authentication authentication) {
        String username = authentication.getName(); 

        LOGGER.info("User {} has been granted a role of a MANAGER by user {}", fullName, username); 

        return ResponseEntity.ok(userService.upgradeToManager(fullName));
    }

     
}