package com.example.libraryapidocker.repository;

import com.example.libraryapidocker.model.Book;
import com.example.libraryapidocker.model.Loan;
import com.example.libraryapidocker.model.LoanStatus;
import com.example.libraryapidocker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);
    List<Loan> findByBook(Book book);
    List<Loan> findByLoanStatus(LoanStatus status);
    List<Loan> findByUserAndLoanStatus(User user, LoanStatus status);

}
