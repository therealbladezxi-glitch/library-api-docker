package com.example.libraryapidocker.exception;

public class MailAlreadyInUseException extends RuntimeException {
    public MailAlreadyInUseException(String mail) {
        super("Mail: " + mail + " already in use.");
    }
}
