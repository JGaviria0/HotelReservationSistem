package com.reservationhotel.reservation.services.implementations;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.web.dto.HotelDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;
import com.reservationhotel.reservation.models.entities.HotelModel;
import com.reservationhotel.reservation.services.interfaces.HotelService;
import com.reservationhotel.reservation.repositories.HotelRepository;

@Service
public class HotelServicempl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public HotelModel mappingDTOToModel(HotelDTO hotel){

        HotelModel hotelModel = HotelModel.builder()
            .hotel_id(hotel.getHotel_id() != null ? hotel.getHotel_id() : null)
            .name(hotel.getName())
            .email(hotel.getEmail())
            .phone(hotel.getPhone())
            .noRooms(hotel.getNoRooms())
            .noSingle(hotel.getNoSingle())
            .noDouble(hotel.getNoDouble())
            .noTriple(hotel.getNoTriple())
            .noQuad(hotel.getNoQuad())
            .capacity(hotel.getCapacity())
            .location(hotel.getLocation())
            .imagen(hotel.getImagen())
            .owner_id(hotel.getOwner_id())
            .build();

        return hotelModel; 
    }

    public HotelDTO mappingModelToDTO(HotelModel hotel){

        HotelDTO hotelDTO = HotelDTO.builder()
            .hotel_id(hotel.getHotel_id() != null ? hotel.getHotel_id() : null)
            .name(hotel.getName())
            .email(hotel.getEmail())
            .phone(hotel.getPhone())
            .noRooms(hotel.getNoRooms())
            .noSingle(hotel.getNoSingle())
            .noDouble(hotel.getNoDouble())
            .noTriple(hotel.getNoTriple())
            .noQuad(hotel.getNoQuad())
            .capacity(hotel.getCapacity())
            .location(hotel.getLocation())
            .imagen(hotel.getImagen())
            .owner_id(hotel.getOwner_id())
            .build();

        return hotelDTO; 
    }
    
    @Override
    public ArrayList<HotelDTO> getHotel(){
        try{
            ArrayList<HotelModel> users = (ArrayList<HotelModel>) hotelRepository.findAll(); 
            ArrayList<HotelDTO> finalUsers = new ArrayList<HotelDTO>(); 
            for(int i=0; i<users.size(); i++){
                finalUsers.add(this.mappingModelToDTO(users.get(i)));
            }
            return finalUsers; 
        } catch(Exception e){
            throw new CustomBadRequestException("Error, intentelo mas tarde.", HttpStatus.NOT_FOUND.value());
        }
    }


    @Override
    public HotelDTO saveHotel(HotelDTO hotel){
        try {
            hotelRepository.findById(hotel.getHotel_id());
        } catch(Exception e){
            int hotelcapacity = hotel.getNoSingle() + hotel.getNoDouble()*2 + hotel.getNoTriple()*3 + hotel.getNoQuad()*4;
            hotel.setCapacity(hotelcapacity);
            int hotelrooms = hotel.getNoSingle() + hotel.getNoDouble() + hotel.getNoTriple() + hotel.getNoQuad();
            hotel.setNoRooms(hotelrooms);
            
            HotelModel hotelModel = this.mappingDTOToModel(hotel);
            try{
                HotelDTO hotelDTO = this.mappingModelToDTO(hotelRepository.save(hotelModel));
                return hotelDTO; 
            } catch(Exception ee){
                throw new CustomBadRequestException("Error al guardar el nuevo Hotel, Intente Luego", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
        throw new CustomBadRequestException("El Hotel ya existe.", HttpStatus.NOT_ACCEPTABLE.value());
    }

    @Override
    public HotelDTO getHotelById(Long id){
        if (id <= 0) {
            throw new CustomBadRequestException("El Id debe ser mayor a 0.", HttpStatus.BAD_REQUEST.value());
        }
       HotelModel query =  hotelRepository.findById(id)
            .orElseThrow(() -> new CustomBadRequestException("Hotel con ID: " + id + " No existe.", HttpStatus.BAD_REQUEST.value()));
        HotelDTO returnquery = this.mappingModelToDTO(query);
        return returnquery; 
    }

    @Override
    public ArrayList<HotelDTO> getHotelByOwner(Long id){
        
        ArrayList<HotelModel> query =  hotelRepository.findByOwner(id);
        if(query.isEmpty()){
            throw new CustomBadRequestException("El usuario" + id + "No tiene hoteles o no existe." , HttpStatus.NOT_FOUND.value());
        }
        ArrayList<HotelDTO> finalUsers = new ArrayList<HotelDTO>(); 
        for(int i=0; i<query.size(); i++){
            finalUsers.add(this.mappingModelToDTO(query.get(i)));
        }
        return finalUsers; 
    }

    @Override
    public HotelDTO updateHotelById (HotelDTO request, Long id){
        
        
        HotelModel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new CustomBadRequestException("Error al editar hotel con id: " + id + " NO existe", HttpStatus.NOT_FOUND.value()));
        System.out.println(request);
        System.out.println(hotel);
        hotel.setName(request.getName());
        hotel.setEmail(request.getEmail());
        hotel.setPhone(request.getPhone());
        hotel.setNoSingle(request.getNoSingle());
        hotel.setNoDouble(request.getNoDouble());
        hotel.setNoTriple(request.getNoTriple());
        hotel.setNoQuad(request.getNoQuad());

        int hotelcapacity = hotel.getNoSingle() + hotel.getNoDouble()*2 + hotel.getNoTriple()*3 + hotel.getNoQuad()*4;
        hotel.setCapacity(hotelcapacity);
        int hotelrooms = hotel.getNoSingle() + hotel.getNoDouble() + hotel.getNoTriple() + hotel.getNoQuad();
        hotel.setNoRooms(hotelrooms);
        
        HotelDTO hotelDTO = this.mappingModelToDTO(hotelRepository.save(hotel));
        return hotelDTO; 
    }

    @Override
    public String deleteHotel(Long id){
        hotelRepository.findById(id)
            .orElseThrow(() -> new CustomBadRequestException("Error al eliminar Hotel con id: " + id + " NO existe", HttpStatus.NOT_FOUND.value()));
        try{
            hotelRepository.deleteById(id);
            return "Eliminado correctamente";
        }catch(Exception e){
            throw new CustomBadRequestException("Error al eliminar hotel con id: " + id , HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
