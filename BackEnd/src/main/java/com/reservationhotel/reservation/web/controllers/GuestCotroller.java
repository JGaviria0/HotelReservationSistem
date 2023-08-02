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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reservationhotel.reservation.services.implementations.GuestServicempl;
import com.reservationhotel.reservation.web.dto.GuestDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;
import com.reservationhotel.reservation.web.exceptions.ErrorResponse;

@RestController
@RequestMapping("/guest")
public class GuestCotroller {
    @Autowired
    private GuestServicempl guestService;

    @PostMapping
    public ResponseEntity<GuestDTO> saveUser(@RequestBody GuestDTO guest) {
        return new ResponseEntity<>(this.guestService.saveGuest(guest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<GuestDTO>> getguestbyUser(@PathVariable Long id){
        return new ResponseEntity<>(this.guestService.getGuestByuser(id), HttpStatus.OK);
    }

    @GetMapping("/{hotel_id}/{user_id}")
    public ResponseEntity<ArrayList<GuestDTO>> getguestbyhotelanduser(@PathVariable("hotel_id") Long hotel_id, @PathVariable("user_id") Long user_id){
        return new ResponseEntity<>(this.guestService.getGuestByuserandhotel(hotel_id, user_id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{hotel_id}/{user_id}/{document}")
    public ResponseEntity<String> deleteById(@PathVariable("hotel_id") Long hotel_id, @PathVariable("user_id") Long user_id, @PathVariable("document") Long document){
        return new ResponseEntity<>(this.guestService.deleteGuest(hotel_id, user_id, document), HttpStatus.OK);
    }
    
    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomBadRequestException(CustomBadRequestException ex) {
        return new ErrorResponse(LocalDateTime.now(), ex.getStatus(), ex.getMessage());
    }
}   
