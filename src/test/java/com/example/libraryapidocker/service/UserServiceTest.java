package com.example.libraryapidocker.service;

import com.example.libraryapidocker.dto.request.UserRequestDTO;
import com.example.libraryapidocker.dto.response.UserResponseDTO;
import com.example.libraryapidocker.exception.MailAlreadyInUseException;
import com.example.libraryapidocker.exception.UserNotFoundException;
import com.example.libraryapidocker.mapper.UserMapper;
import com.example.libraryapidocker.model.User;
import com.example.libraryapidocker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsersExists(){
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void getAllUsers_shouldReturnLisOfUsers_whenUsersExist(){
        //Arrange
        User user = new User();
        user.setId(1L);
        user.setName("Bladez");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setName("Bladez");

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponseDTO);

        //Act
        List<UserResponseDTO> result = userService.getAllUsers();

        //Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Bladez");
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException_whenUserDoesntExist(){
        when(userRepository.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(9L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists(){
        User user = new User();
        user.setId(1L);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createUser_shouldThrowMailAlreadyInUseException_whenMailAlreadyInUse(){
        User user = new User();
        user.setMail("c@l.c");
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setMail("c@l.c");
        when(userRepository.findByMail("c@l.c")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.createUser(userRequestDTO)).isInstanceOf(MailAlreadyInUseException.class);
    }

    @Test
    void createUser_shouldReturnResponse_whenUserIsCorrect(){
        User user = new User();
        user.setMail("c@l.c");
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setMail("c@l.c");
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setMail("c@l.c");

        when(userRepository.findByMail(userRequestDTO.getMail())).thenReturn(Optional.empty());
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponseDTO);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertThat(result.getMail()).isEqualTo("c@l.c");
    }
}
