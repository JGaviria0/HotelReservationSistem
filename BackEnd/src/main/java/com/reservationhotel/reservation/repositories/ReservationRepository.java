package com.reservationhotel.reservation.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.reservationhotel.reservation.models.entities.ReservationModel;

import jakarta.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long>{
    
    @Query(value = "SELECT * FROM reservation WHERE hotel_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<ReservationModel> findByCoID(Long hotel_id, Long user_id);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reservation WHERE hotel_id = ?1 AND user_id = ?2", nativeQuery = true)
    void deleteByCoId(Long hotel_id, Long user_id);

    @Query(value = "SELECT * FROM reservation WHERE hotel_id = ?1", nativeQuery = true)
    ArrayList<ReservationModel> findByHotel(Long hotel_id);

    @Query(value = "SELECT * FROM reservation WHERE user_id = ?1", nativeQuery = true)
    ArrayList<ReservationModel> findByUser(Long user_id);

    @Query( value = "SELECT r.* FROM reservation r" +
                    " INNER JOIN guest g ON r.hotel_id = g.hotel_id AND r.user_id = g.principal_client_id" +
                    " WHERE g.document = ?1", 
            nativeQuery = true)
    ArrayList<ReservationModel> findByGuest(Long document);

    @Query( value = " SELECT" +
                "    hotel_id," +
                "    SUM(no_single) AS available_single," +
                "    SUM(no_double) AS available_double," +
                "    SUM(no_triple) AS available_triple," +
                "    SUM(no_quad) AS available_quad" +
                " FROM" +
                "    reservation " +
                " WHERE" +
                "    hotel_id = ?1 AND (" +
                "        (init_date <= ?2 AND ?2 < end_date) OR" +
                "        (init_date <= ?2 AND ?3 < end_date) OR" +
                "        (init_date >- ?3 AND ?3 > end_date) OR" +
                "        (init_date <= ?3 AND ?3 < end_date) " +
                "    )" +
                " GROUP BY" +
                "    hotel_id;"  , 
            nativeQuery = true)
	ArrayList<Object[]> disponibility(Long hotel_id, String init_date, String end_date);

}
