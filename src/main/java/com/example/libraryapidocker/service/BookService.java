package com.example.libraryapidocker.service;

import com.example.libraryapidocker.dto.request.BookRequestDTO;
import com.example.libraryapidocker.dto.response.BookResponseDTO;
import com.example.libraryapidocker.exception.BookNotFoundException;
import com.example.libraryapidocker.mapper.BookMapper;
import com.example.libraryapidocker.model.Book;
import com.example.libraryapidocker.model.LoanStatus;
import com.example.libraryapidocker.repository.BookRepository;
import com.example.libraryapidocker.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final LoanRepository loanRepository;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, LoanRepository loanRepository){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.loanRepository = loanRepository;
    }

    public List<BookResponseDTO> getAllBooks(){
        return bookRepository.findAll().stream().map(this::toResponseWithCopies).toList();
    }

    public BookResponseDTO getBookByIsbn(UUID isbn){
        return bookRepository.findById(isbn).map(this::toResponseWithCopies).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    private List<BookResponseDTO> getBookByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(this::toResponseWithCopies).toList();
    }

    private List<BookResponseDTO> getBookByAuthor(String authorName){
        return bookRepository.findByAuthorNameContainingIgnoreCase(authorName).stream().map(this::toResponseWithCopies).toList();
    }

    private List<BookResponseDTO> getBookByTitleAndAuthorName(String title, String authorName){
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(title, authorName).stream().map(this::toResponseWithCopies).toList();
    }

    public List<BookResponseDTO> searchBook(String title, String authorName){
        if (title != null && authorName != null){
            return getBookByTitleAndAuthorName(title, authorName);
        } else if (title != null) {
            return getBookByTitle(title);
        } else if (authorName != null) {
            return getBookByAuthor(authorName);
        }
        return List.of();
    }

    public BookResponseDTO createBook(BookRequestDTO requestDTO){
        Book book = bookMapper.toEntity(requestDTO);
        Book savedBook = bookRepository.save(book);
        return toResponseWithCopies(savedBook);
    }

    public void deleteBook(UUID isbn){
        if (!bookRepository.existsById(isbn)){
            throw new BookNotFoundException(isbn);
        }
        bookRepository.deleteById(isbn);
    }

    private int calculateAvailableCopies(Book book){
        int activeLoans = loanRepository.countByBookAndLoanStatus(book, LoanStatus.ACTIVE);
        return book.getTotalCopies() - activeLoans;
    }

    private BookResponseDTO toResponseWithCopies(Book book){
        BookResponseDTO bookResponseDTO = bookMapper.toResponse(book);
        bookResponseDTO.setAvailableCopies(calculateAvailableCopies(book));
        return bookResponseDTO;
    }


}
