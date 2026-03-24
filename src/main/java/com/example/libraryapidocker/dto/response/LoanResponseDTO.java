package com.example.libraryapidocker.dto.response;

import com.example.libraryapidocker.model.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class LoanResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private UUID bookIsbn;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus loanStatus;
}
