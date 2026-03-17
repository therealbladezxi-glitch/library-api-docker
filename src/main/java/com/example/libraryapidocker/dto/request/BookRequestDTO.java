package com.example.libraryapidocker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String authorName;
    private String publisher;
    private Integer releaseYear;
    @NotNull(message = "Copies are required")
    @Min(value = 1, message = "Copies must be at least 1")
    private Integer totalCopies;

}
