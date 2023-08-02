package com.reservationhotel.reservation.services.interfaces;
import com.reservationhotel.reservation.web.dto.GuestDTO;

import java.util.ArrayList;

public interface GuestService {
    
    public GuestDTO saveGuest(GuestDTO guest);

    public ArrayList<GuestDTO> getGuestByuser(Long id);

    public ArrayList<GuestDTO> getGuestByhotel(Long id);

    public String deleteGuest(Long hote_id, Long user_id, Long document);

    public ArrayList<GuestDTO> getGuestByuserandhotel(Long hotel_id, Long user_id);
}
