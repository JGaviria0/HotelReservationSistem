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
@Table(name = "user")
public class UserModel {
    @Id
    private Long document; 
    @Column
    private String name; 
    @Column
    private String phone; 
    @Column(name = "document_type")
    private String documentType; 
    @Column(name = "user_type")
    private String userType; 
    @Column
    private String password; 
}
