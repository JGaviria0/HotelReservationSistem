package com.reservationhotel.reservation.services.interfaces;
import java.time.LocalDate;
import java.util.ArrayList;

import com.reservationhotel.reservation.models.entities.DisponibilityModel;
import com.reservationhotel.reservation.models.entities.ReservationID;
import com.reservationhotel.reservation.web.dto.ReservationDTO;

public interface ReservationService {
    
    public ArrayList<ReservationDTO> getReservation();

    public ReservationDTO saveReservation(ReservationDTO user);

    public ReservationDTO getReservationById(ReservationID id);

    public ReservationDTO updateReservationById (ReservationDTO request, ReservationID id);

    public ArrayList<ReservationDTO> getReservationByHotel(Long id);
    
    public ArrayList<ReservationDTO> getReservationByUser(Long id);

    public String deleteReservation(ReservationID id);

    public ArrayList<ReservationDTO> getReservationByGuest(Long id);

    public DisponibilityModel disponibility ( Long hotel_id, String init_date, String end_date);
}
