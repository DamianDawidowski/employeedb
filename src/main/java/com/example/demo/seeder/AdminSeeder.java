package com.example.demo.seeder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.entities.RoleEnum;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> { 
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder( 
        UserRepository  userRepository,
        PasswordEncoder passwordEncoder
    ) { 
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdministrator();
    }

    private void createAdministrator() {
        
 
        ArrayList<RoleEnum> rolesList = new ArrayList<>(); 
        rolesList.add(RoleEnum.USER);
        rolesList.add(RoleEnum.ADMIN);


        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullName("Admin");
        userDto.setEmail("admin@email.com");
        userDto.setPassword("123321");

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
  
        if (optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(rolesList);

       userRepository.save(user);
    }
}