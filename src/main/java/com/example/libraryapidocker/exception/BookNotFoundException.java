package com.example.libraryapidocker.exception;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(UUID isbn) {
        super("Book not found with isbn: " + isbn);
    }
}
