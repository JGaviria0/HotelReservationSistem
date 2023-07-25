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

import com.reservationhotel.reservation.services.implementations.HotelServicempl;
import com.reservationhotel.reservation.web.dto.HotelDTO;


@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelServicempl hotelService; 

    @GetMapping
    public ArrayList<HotelDTO> getHotel(){
        return this.hotelService.getHotel();
    }

    @PostMapping
    public HotelDTO saveUser(@RequestBody HotelDTO hotel) {
        return this.hotelService.saveHotel(hotel);
    }
    
    @GetMapping(path = "/{id}")
    public HotelDTO getHotelById(@PathVariable Long id){
        return this.hotelService.getHotelById(id);
    }

    @PutMapping(path = "/{id}")
    public HotelDTO updateHotelById(@RequestBody HotelDTO request, @PathVariable("id") Long id){
        return this.hotelService.updateHotelById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.hotelService.deleteHotel(id);
        
        if(ok){
            return "User with id " + id + " deleted succesfully.";
        }

        return "Error, We have a problem and we cant delete user with id " + id; 
    }
    
}
