package com.example.libraryapidocker.service;

import com.example.libraryapidocker.dto.request.LoanRequestDTO;
import com.example.libraryapidocker.dto.response.LoanResponseDTO;
import com.example.libraryapidocker.exception.*;
import com.example.libraryapidocker.mapper.LoanMapper;
import com.example.libraryapidocker.model.*;
import com.example.libraryapidocker.repository.BookRepository;
import com.example.libraryapidocker.repository.LoanRepository;
import com.example.libraryapidocker.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, UserRepository userRepository, BookRepository bookRepository){
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO){
        User user = userRepository.findById(loanRequestDTO.getUserId()).orElseThrow(() -> new UserNotFoundException(loanRequestDTO.getUserId()));
        Book book = bookRepository.findById(loanRequestDTO.getBookIsbn()).orElseThrow(() -> new BookNotFoundException(loanRequestDTO.getBookIsbn()));

        if (user.getUserStatus() == UserStatus.INACTIVE){
            throw new UserInactiveException(user.getId());
        }

        if (noAvailableCopies(loanRepository.countByBookAndLoanStatus(book, LoanStatus.ACTIVE), book)){
            throw new NoAvailableCopiesException(book.getTitle());
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(calculateDueDate(user));
        loan.setLoanStatus(LoanStatus.ACTIVE);

        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toResponse(savedLoan);
    }

    private LocalDate calculateDueDate(User user){
        return switch (user.getUserType()){
            case USER -> LocalDate.now().plusDays(30);
            case ADMIN -> LocalDate.now().plusDays(60);
            case SUPER -> LocalDate.now().plusDays(90);
        };
    }

    private boolean noAvailableCopies(int activeLoans, Book book){
        return activeLoans >= book.getTotalCopies();
    }

    public List<LoanResponseDTO> getAllLoans(){
        return loanRepository.findAll().stream().map(loanMapper::toResponse).toList();
    }

    public List<LoanResponseDTO> getLoanByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return loanRepository.findByUser(user).stream().map(loanMapper::toResponse).toList();
    }

    public void returnLoan(Long id){
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new LoanNotFoundException(id));

        if (loan.getLoanStatus() == LoanStatus.RETURNED){
            throw new LoanAlreadyReturnedException(id);
        }
        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus(LoanStatus.RETURNED);
        loanRepository.save(loan);
    }
}
