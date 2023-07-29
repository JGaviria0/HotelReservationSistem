package com.reservationhotel.reservation.services.implementations;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationhotel.reservation.models.entities.UserModel;
import com.reservationhotel.reservation.repositories.UserRepository;
import com.reservationhotel.reservation.services.interfaces.UserService;
import com.reservationhotel.reservation.web.dto.UserDTO;

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

        ArrayList<UserModel> users = (ArrayList<UserModel>) userRepository.findAll(); 
        ArrayList<UserDTO> finalUsers = new ArrayList<UserDTO>(); 
        for(int i=0; i<users.size(); i++){
            finalUsers.add(this.mappingModelToDTO(users.get(i)));
        }
        return finalUsers; 
    }

    @Override
    public UserDTO saveUser(UserDTO user){
        // if(user.getName().isEmpty()) {
        //     throw new BadRequestException("The user have to have FirstName.");
        // }

        UserModel userModel = this.mappingDTOToModel(user);
        UserDTO userDTO = this.mappingModelToDTO(userRepository.save(userModel));

        return userDTO; 
    }

    @Override
    public UserDTO getUserById(Long id){
        Optional<UserModel> query =  userRepository.findById(id);
        UserDTO returnquery = this.mappingModelToDTO(query.get());
        return returnquery; 
    }

    @Override
    public UserDTO updateUserById (UserDTO request, Long id){
        UserModel user = userRepository.findById(id).get();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setDocumentType(request.getDocumentType());
        user.setUserType(request.getUserType());
        user.setPassword(request.getPassword());
        
        UserDTO userDTO = this.mappingModelToDTO(userRepository.save(user));
        return userDTO; 
    }

    @Override
    public Boolean deleteUser(Long id){
        try{
            userRepository.deleteById(id);
            return true; 
        }catch(Exception e){
            return false; 
        }
    }
}
