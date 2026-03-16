package com.example.libraryapidocker.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "books")
@Schema(description = "Book entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID isbn;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String authorName;
    private String publisher;
    private Integer releaseYear;
    @Column(nullable = false)
    private int totalCopies;
}
