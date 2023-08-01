package com.reservationhotel.reservation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GuestDTO {
    private Long guest_id; 
    private Long hotel_id; 
    private Long principal_client_id;
    private Long document;  
}

    
