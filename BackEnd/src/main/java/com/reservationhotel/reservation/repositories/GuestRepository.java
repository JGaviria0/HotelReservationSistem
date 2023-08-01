package com.reservationhotel.reservation.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservationhotel.reservation.models.entities.GuestModel;


public interface GuestRepository extends JpaRepository<GuestModel, Long> {

    @Query(value = "SELECT * FROM guest WHERE document = ?1", nativeQuery = true)
    ArrayList<GuestModel> findByUser(Long document);

    @Query(value = "SELECT * FROM guest WHERE hotel_id = ?1", nativeQuery = true)
    ArrayList<GuestModel> findByHotel(Long hotel_id);

    @Query(value = "SELECT * FROM guest WHERE hotel_id = ?1 AND principal_client_id = ?2 AND document =?3", nativeQuery = true)
    GuestModel findByhoteluserdocument(Long hotel_id, Long user_id, Long document);
}
