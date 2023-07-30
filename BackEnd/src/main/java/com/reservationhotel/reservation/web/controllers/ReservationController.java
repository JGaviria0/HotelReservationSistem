package com.reservationhotel.reservation.web.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservationhotel.reservation.models.entities.ReservationID;
import com.reservationhotel.reservation.services.implementations.ReservationServicempl;
import com.reservationhotel.reservation.web.dto.ReservationDTO;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationServicempl reservationService; 

    @GetMapping
    public ArrayList<ReservationDTO> getReservation(){
        return this.reservationService.getReservation();
    }

    @PostMapping
    public ReservationDTO saveUser(@RequestBody ReservationDTO reservation) {
        return this.reservationService.saveReservation(reservation);
    }
    
    @GetMapping("/getByID")
    public ReservationDTO getReservationById(@RequestBody ReservationID id){
        return this.reservationService.getReservationById(id);
    }

    // @PutMapping()
    // public String updateReservationById(@RequestBody ReservationDTO request, @RequestParam String hotel_id){
    //     return "respuesta" + hotel_id;
    // }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.reservationService.deleteReservation(id);
        
        if(ok){
            return "Reservation with id " + id + " deleted succesfully.";
        }

        return "Error, We have a problem and we cant delete reservation with id " + id; 
    }
}
