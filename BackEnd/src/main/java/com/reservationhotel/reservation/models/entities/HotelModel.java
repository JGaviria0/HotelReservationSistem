package com.reservationhotel.reservation.models.entities;

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
@Table(name = "hotel")
public class HotelModel {
    
    @Id
    private Long hotel_id; 
    @Column
    private String name; 
    @Column
    private String email;
    @Column
    private String phone;
    @Column(name = "number_of_rooms")
    private int noRooms; 
    @Column(name = "no_single")
    private int noSingle; 
    @Column(name = "no_double")
    private int noDouble; 
    @Column(name = "no_triple")
    private int noTriple;
    @Column(name = "no_quad")
    private int noQuad;  
    @Column
    private int capacity;
}
