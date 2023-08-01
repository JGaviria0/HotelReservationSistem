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
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservationhotel.reservation.models.entities.ReservationID;
import com.reservationhotel.reservation.services.implementations.ReservationServicempl;
import com.reservationhotel.reservation.web.dto.ReservationDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;
import com.reservationhotel.reservation.web.exceptions.ErrorResponse;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationServicempl reservationService; 

    @GetMapping
    public ResponseEntity<ArrayList<ReservationDTO>> getReservation(){
        return new ResponseEntity<>(this.reservationService.getReservation(), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservation) {
        return new ResponseEntity<>(this.reservationService.saveReservation(reservation), HttpStatus.OK);
    }
    
    @GetMapping("/getByID/{hotel_id}/{user_id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable("hotel_id") Long hotel_id, @PathVariable("user_id") Long user_id){
        return new ResponseEntity<>(this.reservationService.getReservationById(new ReservationID(hotel_id, user_id)), HttpStatus.OK);
    }

    @GetMapping("/getByHotel/{hotel_id}")
    public ResponseEntity<ArrayList<ReservationDTO>> getReservationByHotel(@PathVariable("hotel_id") Long hotel_id){
        return new ResponseEntity<>(this.reservationService.getReservationByHotel(hotel_id), HttpStatus.OK);
    }

    @GetMapping("/getByUser/{user_id}")
    public ResponseEntity<ArrayList<ReservationDTO>> getReservationByUser(@PathVariable("user_id") Long user_id){
        return new ResponseEntity<>(this.reservationService.getReservationByUser(user_id), HttpStatus.OK);
    }

    // @PutMapping()
    // public String updateReservationById(@RequestBody ReservationDTO request, @RequestParam String hotel_id){
    //     return "respuesta" + hotel_id;
    // }

    // @DeleteMapping(path = "/{id}")
    // public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
    //     return new ResponseEntity<>(this.reservationService.deleteReservation(id), HttpStatus.OK);
    //     // boolean ok = this.reservationService.deleteReservation(id);
        
    //     // if(ok){
    //     //     return "Reservation with id " + id + " deleted succesfully.";
    //     // }

    //     // return "Error, We have a problem and we cant delete reservation with id " + id; 
    // }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomBadRequestException(CustomBadRequestException ex) {
        return new ErrorResponse(LocalDateTime.now(), ex.getStatus(), ex.getMessage());
    }
}
