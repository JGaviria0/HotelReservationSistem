package com.reservationhotel.reservation.services.interfaces;
import java.util.ArrayList;

import com.reservationhotel.reservation.models.entities.ReservationID;
import com.reservationhotel.reservation.web.dto.ReservationDTO;

public interface ReservationService {
    
    public ArrayList<ReservationDTO> getReservation();

    public ReservationDTO saveReservation(ReservationDTO user);

    public ReservationDTO getReservationById(ReservationID id);

    public ReservationDTO updateReservationById (ReservationDTO request, Long id);

    public ArrayList<ReservationDTO> getReservationByHotel(Long id);
    
    public ArrayList<ReservationDTO> getReservationByUser(Long id);

    public Boolean deleteReservation(Long id);
}
