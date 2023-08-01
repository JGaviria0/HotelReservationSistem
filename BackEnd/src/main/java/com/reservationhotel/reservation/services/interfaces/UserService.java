package com.reservationhotel.reservation.services.interfaces;
import com.reservationhotel.reservation.web.dto.UserDTO;

import java.util.ArrayList;

public interface UserService {
    
    public ArrayList<UserDTO> getUser();

    public UserDTO saveUser(UserDTO user);

    public UserDTO getUserById(Long id);

    public UserDTO updateUserById (UserDTO request, Long id);

    public String deleteUser(Long id);
}
