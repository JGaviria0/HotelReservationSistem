package com.reservationhotel.reservation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservationhotel.reservation.models.entities.ReservationModel;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long>{
    
    @Query(value = "SELECT * FROM reservation WHERE hotel_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<ReservationModel> findByCoID(Long hotel_id, Long user_id);
}
