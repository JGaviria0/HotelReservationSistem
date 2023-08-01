package com.reservationhotel.reservation.web.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reservationhotel.reservation.services.implementations.UserServicempl;
import com.reservationhotel.reservation.web.dto.UserDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;
import com.reservationhotel.reservation.web.exceptions.ErrorResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserServicempl userService; 

    @GetMapping
    public ResponseEntity<ArrayList<UserDTO>> getUser(){
        return new ResponseEntity<>(this.userService.getUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(this.userService.saveUser(user), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public  ResponseEntity<UserDTO> updateUserById(@RequestBody UserDTO request, @PathVariable("id") Long id){
        return new ResponseEntity<>(this.userService.updateUserById(request, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.OK);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomBadRequestException(CustomBadRequestException ex) {
        return new ErrorResponse(LocalDateTime.now(), ex.getStatus(), ex.getMessage());
    }
}


