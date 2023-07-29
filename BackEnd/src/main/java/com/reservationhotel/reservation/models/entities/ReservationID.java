package com.reservationhotel.reservation.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Embeddable
public class ReservationID implements Serializable{
        @Column
        private Long hotel_id;
        @Column
        private Long user_id;
}