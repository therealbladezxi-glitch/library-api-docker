package com.example.libraryapidocker.controller;

import com.example.libraryapidocker.dto.request.BookRequestDTO;
import com.example.libraryapidocker.dto.response.BookResponseDTO;
import com.example.libraryapidocker.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable UUID isbn){
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

//    @GetMapping("/search")
//    public List<BookResponseDTO> getBookByTitle(@RequestParam String title){
//        return bookService.getBookByTitle(title);
//    }
//
//    @GetMapping("/search")
//    public List<BookResponseDTO> getBookByAuthorName(@RequestParam String authorName){
//        return bookService.getBookByAuthor(authorName);
//    }

    @GetMapping("/search")
    public List<BookResponseDTO> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName
    ){
        return bookService.searchBook(title, authorName);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(requestDTO));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<BookResponseDTO> deleteBookByIsbn(@PathVariable UUID isbn){
        bookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }
}
