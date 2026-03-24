package com.example.libraryapidocker.exception;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException(Long id) {
        super("User with id: " + id + " inactive.");
    }
}
