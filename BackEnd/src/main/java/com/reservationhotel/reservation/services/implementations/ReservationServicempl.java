package com.reservationhotel.reservation.services.implementations;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.models.entities.ReservationID;
import com.reservationhotel.reservation.models.entities.ReservationModel;
import com.reservationhotel.reservation.repositories.ReservationRepository;
import com.reservationhotel.reservation.services.interfaces.ReservationService;
import com.reservationhotel.reservation.web.dto.ReservationDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;

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
    public ReservationDTO saveReservation(ReservationDTO reservation){

        if(!reservationRepository.findByCoID(reservation.getHotel_id(), reservation.getUser_id()).isEmpty()){
            System.out.println("ya existe");
            throw new CustomBadRequestException("Ya existe la reserva", HttpStatus.NOT_ACCEPTABLE.value());
        }

        ReservationModel reservationModel = this.mappingDTOToModel(reservation);
        try{
            ReservationDTO reservationDTO = this.mappingModelToDTO(reservationRepository.save(reservationModel));
            return reservationDTO;
        } catch(Exception err){
            throw new CustomBadRequestException("Error al crear la reserva, Intente Luego", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public ReservationDTO getReservationById(ReservationID id) {
        try{
            Optional<ReservationModel> query = reservationRepository.findByCoID(id.getHotel_id(), id.getUser_id());
            ReservationDTO returnquery = this.mappingModelToDTO(query.get());
        return returnquery;
        } catch (Exception e){
            throw new CustomBadRequestException("Reserva con id usrio: " + id + " No existe.", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public ArrayList<ReservationDTO> getReservationByHotel(Long id) {
        try{
            ArrayList<ReservationModel> query;
            try{
                query =  (ArrayList<ReservationModel>)reservationRepository.findByHotel(id);
            } catch (Exception e){
                throw new CustomBadRequestException("Reserva con id usrio: " + id + " No existe.", HttpStatus.BAD_REQUEST.value());
            }
            ArrayList<ReservationDTO> finalReservations = new ArrayList<ReservationDTO>();
            for (int i = 0; i < query.size(); i++) {
                finalReservations.add(this.mappingModelToDTO(query.get(i)));
            }
            return finalReservations;
        } catch(Exception err) {
            System.out.println(err);
            throw new CustomBadRequestException("No se pudo consultar, intente luego.", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public ArrayList<ReservationDTO> getReservationByUser(Long id) {
        try{
            ArrayList<ReservationModel> query;
            try{
                query =  (ArrayList<ReservationModel>)reservationRepository.findByUser(id);
            } catch (Exception e){
                throw new CustomBadRequestException("Reserva con id usrio: " + id + " No existe.", HttpStatus.BAD_REQUEST.value());
            }
            ArrayList<ReservationDTO> finalReservations = new ArrayList<ReservationDTO>();
            for (int i = 0; i < query.size(); i++) {
                finalReservations.add(this.mappingModelToDTO(query.get(i)));
            }
            return finalReservations;
        } catch(Exception err) {
            System.out.println(err);
            throw new CustomBadRequestException("No se pudo consultar, intente luego.", HttpStatus.BAD_REQUEST.value());
        }
        
    }

    @Override
    public ReservationDTO updateReservationById (ReservationDTO request, ReservationID id){
        ReservationModel reservation = reservationRepository.findByCoID(id.getHotel_id(), id.getUser_id())
            .orElseThrow(() -> new CustomBadRequestException("Error al editar la reserva con id: " + id + " NO existe", HttpStatus.NOT_FOUND.value()));
        reservation.setInit_date(request.getInit_date());
        reservation.setEnd_date(request.getEnd_date());
        reservation.setStatus(request.getStatus());
        
        ReservationDTO reservationDTO = this.mappingModelToDTO(reservationRepository.save(reservation));
        return reservationDTO; 
    }

    @Override
    public String deleteReservation(ReservationID id) {
        reservationRepository.findByCoID(id.getHotel_id(), id.getUser_id())
            .orElseThrow(() -> new CustomBadRequestException("Error al eliminar user con id: " + id + " NO existe", HttpStatus.NOT_FOUND.value()));
        try{
            reservationRepository.deleteByCoId(id.getHotel_id(), id.getUser_id());
            return "Eliminado correctamente";
        }catch(Exception e){
            System.out.println(e);
            throw new CustomBadRequestException("Error al eliminar reservation con id: " + id.getHotel_id() + id.getUser_id() , HttpStatus.BAD_REQUEST.value());
        }
    }
}
