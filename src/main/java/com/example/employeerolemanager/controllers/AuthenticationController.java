package com.example.employeerolemanager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeerolemanager.dtos.LoginUserDto;
import com.example.employeerolemanager.dtos.RegisterUserDto;
import com.example.employeerolemanager.entities.User;
import com.example.employeerolemanager.instances.LoginResponse;
import com.example.employeerolemanager.services.AuthenticationService;
import com.example.employeerolemanager.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    ProblemDetail errorDetail = null;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        LOGGER.info("New signed up attempts using email: {}", registerUserDto.getEmail()); 

        User registeredUser = authenticationService.signup(registerUserDto);
        LOGGER.info("New user signed up using email: {}", registerUserDto.getEmail()); 
      
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        LOGGER.info("New login attempts using email: {}", loginUserDto.getEmail()); 
        
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        LOGGER.info("A user logged in using email: {}", loginUserDto.getEmail()); 

        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken); 
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}