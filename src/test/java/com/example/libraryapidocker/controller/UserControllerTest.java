package com.example.libraryapidocker.controller;

import com.example.libraryapidocker.dto.request.UserRequestDTO;
import com.example.libraryapidocker.dto.response.UserResponseDTO;
import com.example.libraryapidocker.exception.UserNotFoundException;
import com.example.libraryapidocker.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @Test
    void getAllUsers_shouldReturn200_whenUsersExist() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllUsers_shouldReturn200WithEmptyList_whenNoUserExists() throws Exception{
        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getUserByid_shouldReturnUser_whenUserExists() throws Exception{
        UserResponseDTO userResponseDTO = buildUserResponse();
        when(userService.getUserById(1L)).thenReturn(userResponseDTO);

        mockMvc.perform(get("/user/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("BladezTest"))
                .andExpect(jsonPath("$.mail").value("c@test.lc"));
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException_whenUserDoesNotExist() throws Exception{
        when(userService.getUserById(99L)).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(get("/user/99"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private UserRequestDTO buildUserRequest(){
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("BladezTest");
        dto.setMail("c@test.lc");
        return dto;
    }

    private UserResponseDTO buildUserResponse(){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setName("BladezTest");
        dto.setMail("c@test.lc");
        return dto;
    }
}
