package com.example.libraryapidocker.exception;

public class NoAvailableCopiesException extends RuntimeException {
    public NoAvailableCopiesException(String title) {
        super("No available copies for " + title);
    }
}
