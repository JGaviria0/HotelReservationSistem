package com.reservationhotel.reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservationhotel.reservation.models.entities.UserModel;


public interface UserRepository extends JpaRepository<UserModel, Long> {

    
}
