package com.reservationhotel.reservation.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {
    private Long   document; 
    private String name; 
    private String phone; 
    private String documentType; 
    private String userType; 
    private String password; 
}