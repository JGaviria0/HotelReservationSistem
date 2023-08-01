package com.reservationhotel.reservation.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "guest")
public class GuestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guest_id; 
    @Column
    private Long hotel_id; 
    @Column
    private Long principal_client_id;
    @Column
    private Long document;  
}


    

