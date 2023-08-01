package com.reservationhotel.reservation.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class ReservationID implements Serializable{
        @Column
        private Long hotel_id;
        @Column
        private Long user_id;

        public ReservationID() { }
        
        public ReservationID(Long hotel_id, Long user_id) {
        this.hotel_id = hotel_id;
        this.user_id = user_id;
        }
}