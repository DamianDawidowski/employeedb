package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

import java.util.Optional;
import java.util.List;
import com.example.demo.entities.RoleEnum;
import java.util.ArrayList;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findByFullName(String fullName);
    Optional<User> findByRoles(ArrayList<RoleEnum> roles);
}