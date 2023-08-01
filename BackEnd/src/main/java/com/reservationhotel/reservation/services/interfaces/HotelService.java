package com.reservationhotel.reservation.services.interfaces;
import java.util.ArrayList;

import com.reservationhotel.reservation.web.dto.HotelDTO;


public interface HotelService {
    
    public ArrayList<HotelDTO> getHotel();

    public HotelDTO saveHotel(HotelDTO user);

    public HotelDTO getHotelById(Long id);

    public HotelDTO updateHotelById (HotelDTO request, Long id);

    public String deleteHotel(Long id);

    public ArrayList<HotelDTO> getHotelByOwner(Long id);
}
