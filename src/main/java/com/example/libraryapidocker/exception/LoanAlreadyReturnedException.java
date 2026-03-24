package com.example.libraryapidocker.exception;

public class LoanAlreadyReturnedException extends RuntimeException {
    public LoanAlreadyReturnedException(Long id) {
        super("Loan already returned with id: " + id);
    }
}
