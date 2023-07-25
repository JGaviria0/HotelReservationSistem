package com.reservationhotel.reservation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HotelDTO {
    
    private Long hotelID; 
    private String name; 
    private String email;
    private String phone;
    private int noRooms; 
    private int noSingle; 
    private int noDouble; 
    private int noTriple;
    private int noQuad; 
    private int capacity; 

}