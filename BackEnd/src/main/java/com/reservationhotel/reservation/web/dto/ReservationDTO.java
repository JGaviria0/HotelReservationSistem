package com.reservationhotel.reservation.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long hotel_id; 
    private Long user_id; 
    private LocalDateTime init_date; 
    private LocalDateTime end_date; 
    private String status; 
}