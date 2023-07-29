package com.reservationhotel.reservation.services.implementations;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.models.entities.ReservationID;
import com.reservationhotel.reservation.models.entities.ReservationModel;
import com.reservationhotel.reservation.repositories.ReservationRepository;
import com.reservationhotel.reservation.services.interfaces.ReservationService;
import com.reservationhotel.reservation.web.dto.ReservationDTO;

@Service
public class ReservationServicempl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationModel mappingDTOToModel(ReservationDTO reservation) {

        ReservationModel reservationModel = ReservationModel.builder()
                .id(new ReservationID(reservation.getHotel_id(), reservation.getUser_id()))
                .init_date(reservation.getInit_date())
                .end_date(reservation.getEnd_date())
                .status(reservation.getStatus())
                .build();

        return reservationModel;
    }

    public ReservationDTO mappingModelToDTO(ReservationModel reservation) {
        ReservationDTO reservationDTO = ReservationDTO.builder()
                .hotel_id(reservation.getId().getHotel_id())
                .user_id(reservation.getId().getUser_id())
                .init_date(reservation.getInit_date())
                .end_date(reservation.getEnd_date())
                .status(reservation.getStatus())
                .build();

        return reservationDTO;
    }

    @Override
    public ArrayList<ReservationDTO> getReservation() {

        ArrayList<ReservationModel> reservations = (ArrayList<ReservationModel>) reservationRepository.findAll();
        System.out.println(reservations);
        ArrayList<ReservationDTO> finalReservations = new ArrayList<ReservationDTO>();
        for (int i = 0; i < reservations.size(); i++) {
            finalReservations.add(this.mappingModelToDTO(reservations.get(i)));
        }
        return finalReservations;
    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservation) {
        // if(reservation.getName().isEmpty()) {
        // throw new BadRequestException("The reservation have to have FirstName.");
        // }

        ReservationModel reservationModel = this.mappingDTOToModel(reservation);
        ReservationDTO reservationDTO = this.mappingModelToDTO(reservationRepository.save(reservationModel));

        return reservationDTO;
    }

    @Override
    public ReservationDTO getReservationById(ReservationID id) {
        Optional<ReservationModel> query = reservationRepository.findByCoID(id.getHotel_id(), id.getUser_id());
        ReservationDTO returnquery = this.mappingModelToDTO(query.get());
        return returnquery;
    }

    @Override
    public ReservationDTO updateReservationById (ReservationDTO request, Long id){
        ReservationModel reservation = reservationRepository.findById(id).get();
        reservation.setId( new ReservationID(request.getHotel_id(), request.getUser_id()));
        reservation.setInit_date(request.getInit_date());
        reservation.setEnd_date(request.getEnd_date());
        reservation.setStatus(request.getStatus());
        
        ReservationDTO reservationDTO = this.mappingModelToDTO(reservationRepository.save(reservation));
        return reservationDTO; 
    }

    @Override
    public Boolean deleteReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
