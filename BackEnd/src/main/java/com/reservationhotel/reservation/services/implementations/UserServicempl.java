package com.reservationhotel.reservation.services.implementations;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.models.entities.UserModel;
import com.reservationhotel.reservation.repositories.UserRepository;
import com.reservationhotel.reservation.services.interfaces.UserService;
import com.reservationhotel.reservation.web.dto.UserDTO;
import com.reservationhotel.reservation.web.exceptions.CustomBadRequestException;

@Service
public class UserServicempl implements UserService {
    
    @Autowired
    private UserRepository userRepository; 

    public UserModel mappingDTOToModel(UserDTO user){

        UserModel userModel = UserModel.builder()
            .document(user.getDocument() != null ? user.getDocument() : null)
            .name(user.getName())
            .phone(user.getPhone())
            .documentType(user.getDocumentType())
            .userType(user.getUserType())
            .password(user.getPassword())
            .build();

        return userModel; 
    }

    public UserDTO mappingModelToDTO(UserModel user){
        UserDTO userDTO = UserDTO.builder()
            .document(user.getDocument() != null ? user.getDocument() : null)
            .name(user.getName())
            .phone(user.getPhone())
            .documentType(user.getDocumentType())
            .userType(user.getUserType())
            .password(user.getPassword())
            .build();

        return userDTO; 
    }

    @Override
    public ArrayList<UserDTO> getUser(){
        try{
            ArrayList<UserModel> users = (ArrayList<UserModel>) userRepository.findAll(); 
            ArrayList<UserDTO> finalUsers = new ArrayList<UserDTO>(); 
            for(int i=0; i<users.size(); i++){
                finalUsers.add(this.mappingModelToDTO(users.get(i)));
            }
            return finalUsers; 
        } catch(Exception e){
            throw new CustomBadRequestException("Error, intentelo mas tarde.", HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public UserDTO saveUser(UserDTO user){
        try {
            userRepository.findById(user.getDocument());            
        } catch(Exception e){
            System.out.println(e);
            UserModel userModel = this.mappingDTOToModel(user);
            try{
                UserModel newuser = userRepository.save(userModel);
                UserDTO userDTO = this.mappingModelToDTO(newuser);
                return userDTO; 
            } catch(Exception ee){
                throw new CustomBadRequestException("Error al guardar el nuevo usuario, Intente Luego", HttpStatus.BAD_REQUEST.value());
            }
        }

        throw new CustomBadRequestException("El usuario ya existe.", HttpStatus.NOT_ACCEPTABLE.value());
    }

    @Override
    public UserDTO getUserById(Long id){
        if (id <= 0) {
            throw new CustomBadRequestException("El Id debe ser mayor a 0.", HttpStatus.BAD_REQUEST.value());
        }
        UserModel query = userRepository.findById(id)
                .orElseThrow(() -> new CustomBadRequestException("Usuario con ID " + id + " No existe.", HttpStatus.BAD_REQUEST.value()));
        UserDTO returnquery = this.mappingModelToDTO(query);
        return returnquery; 
    }

    @Override
    public UserDTO updateUserById (UserDTO request, Long id){
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new CustomBadRequestException("Error al editar user con id: " + id + " NO existe", HttpStatus.NOT_FOUND.value()));
        System.out.println(user);
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setDocumentType(request.getDocumentType());
        user.setUserType(request.getUserType());
        user.setPassword(request.getPassword());
        
        UserDTO userDTO = this.mappingModelToDTO(userRepository.save(user));
        return userDTO; 
    }

    @Override
    public String deleteUser(Long id){
        userRepository.findById(id)
            .orElseThrow(() -> new CustomBadRequestException("Error al eliminar user con id: " + id + " NO existe", HttpStatus.NOT_FOUND.value()));
        try{
            userRepository.deleteById(id);
            return "Eliminado correctamente";
        }catch(Exception e){
            throw new CustomBadRequestException("Error al eliminar user con id: " + id , HttpStatus.BAD_REQUEST.value());
        }
    }
}
