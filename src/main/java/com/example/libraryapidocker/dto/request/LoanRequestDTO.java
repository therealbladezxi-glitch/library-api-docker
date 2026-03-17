package com.example.libraryapidocker.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoanRequestDTO {
    private Long userId;
    private UUID bookIsbn;
}
