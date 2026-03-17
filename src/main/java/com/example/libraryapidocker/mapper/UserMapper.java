package com.example.libraryapidocker.mapper;

import com.example.libraryapidocker.dto.request.UserRequestDTO;
import com.example.libraryapidocker.dto.response.UserResponseDTO;
import com.example.libraryapidocker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponse(User user);
    User toEntity(UserRequestDTO requestDTO);
}
