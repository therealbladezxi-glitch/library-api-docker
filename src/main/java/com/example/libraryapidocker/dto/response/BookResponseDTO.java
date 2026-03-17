package com.example.libraryapidocker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookResponseDTO {
    private UUID isbn;
    private String title;
    private String authorName;
    private String publisher;
    private Integer releaseYear;
    private Integer totalCopies;
    private Integer availableCopies;
}
