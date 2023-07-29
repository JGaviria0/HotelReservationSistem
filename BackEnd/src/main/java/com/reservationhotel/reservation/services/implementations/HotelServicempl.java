package com.reservationhotel.reservation.services.implementations;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.web.dto.HotelDTO;
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
            .build();

        return hotelDTO; 
    }
    
    @Override
    public ArrayList<HotelDTO> getHotel(){

        ArrayList<HotelModel> users = (ArrayList<HotelModel>) hotelRepository.findAll(); 
        ArrayList<HotelDTO> finalUsers = new ArrayList<HotelDTO>(); 
        for(int i=0; i<users.size(); i++){
            finalUsers.add(this.mappingModelToDTO(users.get(i)));
        }
        return finalUsers; 
    }


    @Override
    public HotelDTO saveHotel(HotelDTO hotel){
        // if(user.getName().isEmpty()) {
        //     throw new BadRequestException("The user have to have FirstName.");
        // }

        int hotelcapacity = hotel.getNoSingle() + hotel.getNoDouble()*2 + hotel.getNoTriple()*3 + hotel.getNoQuad()*4;
        hotel.setCapacity(hotelcapacity);
        int hotelrooms = hotel.getNoSingle() + hotel.getNoDouble() + hotel.getNoTriple() + hotel.getNoQuad();
        hotel.setNoRooms(hotelrooms);

        HotelModel hotelModel = this.mappingDTOToModel(hotel);
        HotelDTO hotelDTO = this.mappingModelToDTO(hotelRepository.save(hotelModel));

        return hotelDTO; 
    }

    @Override
    public HotelDTO getHotelById(Long id){
        Optional<HotelModel> query =  hotelRepository.findById(id);
        HotelDTO returnquery = this.mappingModelToDTO(query.get());
        return returnquery; 
    }

    @Override
    public HotelDTO updateHotelById (HotelDTO request, Long id){
        HotelModel hotel = hotelRepository.findById(id).get();
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
    public Boolean deleteHotel(Long id){
        try{
            hotelRepository.deleteById(id);
            return true; 
        }catch(Exception e){
            return false; 
        }
    }
}
