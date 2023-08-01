package com.reservationhotel.reservation.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservationhotel.reservation.models.entities.HotelModel;

public interface HotelRepository extends JpaRepository<HotelModel, Long> {

    @Query(value = "SELECT * FROM hotel WHERE owner_id = ?1", nativeQuery = true)
    ArrayList<HotelModel> findByOwner(Long owner_id);

}
