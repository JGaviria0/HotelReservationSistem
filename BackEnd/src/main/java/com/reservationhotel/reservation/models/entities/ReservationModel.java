package com.reservationhotel.reservation.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
public class ReservationModel {
    @EmbeddedId
    private ReservationID   id; 
    @Column
    private LocalDateTime   init_date; 
    @Column
    private LocalDateTime   end_date; 
    @Column
    private String          status; 
    @Column
    private Long no_single; 
    @Column
    private Long no_double; 
    @Column
    private Long no_triple; 
    @Column
    private Long no_quad;

 }
