package com.example.libraryapidocker.dto.response;

import com.example.libraryapidocker.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private UserType userType;
    private LocalDate memberShipDate;
}
