package com.example.libraryapidocker.service;

import com.example.libraryapidocker.dto.request.UserRequestDTO;
import com.example.libraryapidocker.dto.response.UserResponseDTO;
import com.example.libraryapidocker.exception.MailAlreadyInUseException;
import com.example.libraryapidocker.exception.UserNotFoundException;
import com.example.libraryapidocker.mapper.UserMapper;
import com.example.libraryapidocker.model.User;
import com.example.libraryapidocker.model.UserStatus;
import com.example.libraryapidocker.model.UserType;
import com.example.libraryapidocker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    public UserResponseDTO getUserById(Long id){
        return userRepository.findById(id).map(userMapper::toResponse).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO){
        if (userRepository.findByMail(requestDTO.getMail()).isPresent()){
            throw new MailAlreadyInUseException(requestDTO.getMail());
        }

        User user = userMapper.toEntity(requestDTO);
        user.setUserType(UserType.USER);
        user.setMemberShipDate(LocalDate.now());
        user.setUserStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setUserStatus(UserStatus.INACTIVE);
        userRepository.save(user);
//        if (!userRepository.existsById(id)){
//            throw new UserNotFoundException(id);
//        }
//        userRepository.deleteById(id);
    }
}
