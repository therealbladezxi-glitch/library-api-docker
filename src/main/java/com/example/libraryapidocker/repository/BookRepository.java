package com.example.libraryapidocker.repository;

import com.example.libraryapidocker.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);

}
