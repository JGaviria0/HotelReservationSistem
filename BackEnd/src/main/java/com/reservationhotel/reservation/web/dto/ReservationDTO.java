package com.reservationhotel.reservation.web.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long hotel_id; 
    private Long user_id; 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime init_date; 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime end_date; 
    private String status; 
    private Long no_single; 
    private Long no_double; 
    private Long no_triple; 
    private Long no_quad;
}