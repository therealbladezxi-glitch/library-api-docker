package com.example.libraryapidocker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Mail is required")
    private String mail;

    @NotBlank(message = "Password is required")
    private String pass;

}
