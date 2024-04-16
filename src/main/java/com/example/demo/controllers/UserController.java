package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.entities.BasicUser;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    } 

    
    // @GetMapping("/current")
    // public ResponseEntity<User> authenticatedUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //     User currentUser = (User) authentication.getPrincipal();

    //     return ResponseEntity.ok(currentUser);
    // }



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

    // @DeleteMapping("/{id}")
    // public ResponseEntity<FishDto> deleteFish(@PathVariable Long id) {
    //     return ResponseEntity.ok(fishService.deleteFish(id));
    // }
}