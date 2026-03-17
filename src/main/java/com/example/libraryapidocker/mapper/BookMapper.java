package com.example.libraryapidocker.mapper;

import com.example.libraryapidocker.dto.request.BookRequestDTO;
import com.example.libraryapidocker.dto.response.BookResponseDTO;
import com.example.libraryapidocker.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponseDTO toResponse(Book book);
    Book toEntity(BookRequestDTO responseDTO);
}
