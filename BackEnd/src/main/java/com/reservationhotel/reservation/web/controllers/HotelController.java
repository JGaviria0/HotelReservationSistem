package com.reservationhotel.reservation.web.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reservationhotel.reservation.services.implementations.HotelServicempl;
import com.reservationhotel.reservation.web.dto.HotelDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;
import com.reservationhotel.reservation.web.exceptions.ErrorResponse;


@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelServicempl hotelService; 

    @GetMapping
    public ResponseEntity<ArrayList<HotelDTO>> getHotel(){
        return new ResponseEntity<> (this.hotelService.getHotel(), HttpStatus.OK);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<ArrayList<HotelDTO>> getHotelowner(@PathVariable Long id){
        return new ResponseEntity<>(this.hotelService.getHotelByOwner(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HotelDTO> saveUser(@RequestBody HotelDTO hotel) {
        return new ResponseEntity<>(this.hotelService.saveHotel(hotel), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id){
        return new ResponseEntity<>(this.hotelService.getHotelById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<HotelDTO> updateHotelById(@RequestBody HotelDTO request, @PathVariable("id") Long id){
        return new ResponseEntity<>(this.hotelService.updateHotelById(request, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.hotelService.deleteHotel(id), HttpStatus.OK);
    }
    
    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomBadRequestException(CustomBadRequestException ex) {
        return new ErrorResponse(LocalDateTime.now(), ex.getStatus(), ex.getMessage());
    }
}
