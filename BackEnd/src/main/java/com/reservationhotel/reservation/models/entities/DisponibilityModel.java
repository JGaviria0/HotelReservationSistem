package com.reservationhotel.reservation.models.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class DisponibilityModel {
    @Id
    private Long hotel_id; 
    @Column
    private Long available_single; 
    @Column
    private Long available_double; 
    @Column
    private Long available_triple; 
    @Column
    private Long available_quad; 

}