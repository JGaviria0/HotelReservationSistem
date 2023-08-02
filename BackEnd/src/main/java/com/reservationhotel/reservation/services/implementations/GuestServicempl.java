package com.reservationhotel.reservation.services.implementations;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.models.entities.GuestModel;
import com.reservationhotel.reservation.repositories.GuestRepository;
import com.reservationhotel.reservation.services.interfaces.GuestService;
import com.reservationhotel.reservation.web.dto.GuestDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;

@Service
public class GuestServicempl implements GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public GuestModel mappingDTOToModel(GuestDTO user){

        GuestModel userModel = GuestModel.builder()
            .guest_id(user.getGuest_id() != null ? user.getGuest_id() : null)
            .hotel_id(user.getHotel_id())
            .principal_client_id(user.getPrincipal_client_id())
            .document(user.getDocument())
            .build();

        return userModel; 
    }

    public GuestDTO mappingModelToDTO(GuestModel user){
        GuestDTO userDTO = GuestDTO.builder()
            .guest_id(user.getGuest_id() != null ? user.getGuest_id() : null)
            .hotel_id(user.getHotel_id())
            .principal_client_id(user.getPrincipal_client_id())
            .document(user.getDocument())
            .build();

        return userDTO; 
    }
    @Override
    public GuestDTO saveGuest(GuestDTO guest){
        try{
            GuestModel userModel = this.mappingDTOToModel(guest);
            GuestModel newuser = guestRepository.save(userModel);
            GuestDTO userDTO = this.mappingModelToDTO(newuser);
            return userDTO; 
        } catch(Exception ee){
            System.out.println(ee);
            throw new CustomBadRequestException("Error al guardar el nuevo usuario, Intente Luego", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public ArrayList<GuestDTO> getGuestByuser(Long id){
        ArrayList<GuestModel> query =  guestRepository.findByUser(id);
        System.out.println(query);
        if(query.isEmpty()){
            throw new CustomBadRequestException("El usuario" + id + "No tiene reservas o no existe." , HttpStatus.NOT_FOUND.value());
        }
        ArrayList<GuestDTO> finalUsers = new ArrayList<GuestDTO>(); 
        for(int i=0; i<query.size(); i++){
            finalUsers.add(this.mappingModelToDTO(query.get(i)));
        }
        return finalUsers; 
    }

    @Override
    public ArrayList<GuestDTO> getGuestByhotel(Long id){
        ArrayList<GuestModel> query =  guestRepository.findByHotel(id);
        if(query.isEmpty()){
            throw new CustomBadRequestException("El invitado" + id + "No tiene reservas o no existe." , HttpStatus.NOT_FOUND.value());
        }
        ArrayList<GuestDTO> finalUsers = new ArrayList<GuestDTO>(); 
        for(int i=0; i<query.size(); i++){
            finalUsers.add(this.mappingModelToDTO(query.get(i)));
        }
        return finalUsers; 
    }

    @Override
    public ArrayList<GuestDTO> getGuestByuserandhotel(Long hotel_id, Long user_id){
        ArrayList<GuestModel> query =  guestRepository.findByUserAndHotel(hotel_id, user_id);
        if(query.isEmpty()){
            throw new CustomBadRequestException("El invitado" + hotel_id + user_id + "No tiene reservas o no existe." , HttpStatus.NOT_FOUND.value());
        }
        ArrayList<GuestDTO> finalUsers = new ArrayList<GuestDTO>(); 
        for(int i=0; i<query.size(); i++){
            finalUsers.add(this.mappingModelToDTO(query.get(i)));
        }
        return finalUsers; 
    }

    public String deleteGuest(Long hote_id, Long user_id, Long document){

        GuestModel id = guestRepository.findByhoteluserdocument(hote_id, user_id, document);
        System.out.println(id);

        if(id == null){
            throw new CustomBadRequestException("Error al eliminar el invitado, NO existe", HttpStatus.NOT_FOUND.value());
        }

        try{
            guestRepository.deleteById(id.getGuest_id());
            return "Eliminado correctamente";
        }catch(Exception e){
            throw new CustomBadRequestException("Error al eliminar hotel con id: " + id , HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
