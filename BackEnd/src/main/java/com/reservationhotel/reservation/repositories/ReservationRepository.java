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

    // @Query(value = "SELECT h.hotel_id, h.name AS hotel_name, h.no_single AS total_single, h.no_double AS total_double, h.no_triple AS total_triple, h.no_quad AS total_quad," +
    // " COALESCE(h.no_single - IFNULL(s.reserved_single, 0), h.no_single) AS available_single, COALESCE(h.no_double - IFNULL(d.reserved_double, 0), h.no_double) AS available_double," +
    // " COALESCE(h.no_triple - IFNULL(t.reserved_triple, 0), h.no_triple) AS available_triple, COALESCE(h.no_quad - IFNULL(q.reserved_quad, 0), h.no_quad) AS available_quad" +
    // " FROM hotel h" +
    // " LEFT JOIN (SELECT hotel_id, COUNT(*) AS reserved_single FROM reservation WHERE init_date <= :endDate AND end_date >= :startDate AND status = 'Confirmed' GROUP BY hotel_id) s ON h.hotel_id = s.hotel_id" +
    // " LEFT JOIN (SELECT hotel_id, COUNT(*) AS reserved_double FROM reservation WHERE init_date <= :endDate AND end_date >= :startDate AND status = 'Confirmed' GROUP BY hotel_id) d ON h.hotel_id = d.hotel_id" +
    // " LEFT JOIN (SELECT hotel_id, COUNT(*) AS reserved_triple FROM reservation WHERE init_date <= :endDate AND end_date >= :startDate AND status = 'Confirmed' GROUP BY hotel_id) t ON h.hotel_id = t.hotel_id" +
    // " LEFT JOIN (SELECT hotel_id, COUNT(*) AS reserved_quad FROM reservation WHERE init_date <= :endDate AND end_date >= :startDate AND status = 'Confirmed' GROUP BY hotel_id) q ON h.hotel_id = q.hotel_id" +
    // " WHERE h.hotel_id = :hotelId",
    // nativeQuery = true 
    // )ArrayList<ReservationModel> ;
}
