package com.reservationhotel.reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservationhotel.reservation.models.entities.HotelModel;

public interface HotelRepository extends JpaRepository<HotelModel, Long> {

    // List<UserModel> findAllByNombre(String nombre);

}
