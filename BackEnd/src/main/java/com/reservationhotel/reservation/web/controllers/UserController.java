package com.reservationhotel.reservation.web.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationhotel.reservation.services.implementations.UserServicempl;
import com.reservationhotel.reservation.web.dto.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserServicempl userService; 

    @GetMapping
    public ArrayList<UserDTO> getUser(){
        return this.userService.getUser();
    }

    @PostMapping
    public UserDTO saveUser(@RequestBody UserDTO user) {
        return this.userService.saveUser(user);
    }
    
    @GetMapping(path = "/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public UserDTO updateUserById(@RequestBody UserDTO request, @PathVariable("id") Long id){
        return this.userService.updateUserById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.userService.deleteUser(id);
        
        if(ok){
            return "User with id " + id + " deleted succesfully.";
        }

        return "Error, We have a problem and we cant delete user with id " + id; 
    }
}
