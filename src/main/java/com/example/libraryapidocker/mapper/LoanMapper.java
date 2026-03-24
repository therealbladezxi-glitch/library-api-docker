package com.example.libraryapidocker.mapper;

import com.example.libraryapidocker.dto.request.LoanRequestDTO;
import com.example.libraryapidocker.dto.response.LoanResponseDTO;
import com.example.libraryapidocker.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "book.isbn", target = "bookIsbn")
    @Mapping(source = "book.title", target = "bookTitle")
    LoanResponseDTO toResponse(Loan loan);
}
