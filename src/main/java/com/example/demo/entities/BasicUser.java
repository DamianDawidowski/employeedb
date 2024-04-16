package com.example.demo.entities;

import java.util.ArrayList;

public class BasicUser {
   
    private String fullName;
 
    private String email;

    private ArrayList<RoleEnum> roles;



    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public ArrayList<RoleEnum> getRoles() {
        return this.roles;
    }

    public void setRoles(ArrayList<RoleEnum> roles) {
        this.roles = roles;
    }
 
   

}
