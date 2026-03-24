package com.example.libraryapidocker.controller;

import com.example.libraryapidocker.dto.request.BookRequestDTO;
import com.example.libraryapidocker.dto.response.BookResponseDTO;
import com.example.libraryapidocker.exception.BookNotFoundException;
import com.example.libraryapidocker.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final UUID TEST_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @Test
    void getAllBooks_shouldReturn200_whenBooksExist() throws Exception{
        when(bookService.getAllBooks()).thenReturn(List.of(buildBookResponse()));

        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("One Piece"))
                .andExpect(jsonPath("$[0].authorName").value("Oda"));
    }

    @Test
    void getAllBooks_shouldReturn200WithEmptyList_whenNoBookExists()throws Exception{
        when(bookService.getAllBooks()).thenReturn(List.of());

        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getBookByIsbn_shouldReturnBook_whenBookExistWithIsbn() throws Exception{
        when(bookService.getBookByIsbn(TEST_UUID)).thenReturn(buildBookResponse());

        mockMvc.perform(get("/book/" + TEST_UUID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("One Piece"));
    }

    @Test
    void getBookByIsbn_shouldThrowBookNotFound_whenIsbnNoExists() throws Exception{
        when(bookService.getBookByIsbn(TEST_UUID)).thenThrow(new BookNotFoundException(TEST_UUID));

        mockMvc.perform(get("/book/" + TEST_UUID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_shouldReturn200_whenBookCreated() throws Exception{
        when(bookService.createBook(any(BookRequestDTO.class))).thenReturn(buildBookResponse());

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildBookRequest()))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private BookRequestDTO buildBookRequest(){
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitle("One Piece");
        bookRequestDTO.setAuthorName("Oda");
        bookRequestDTO.setTotalCopies(5);
        return bookRequestDTO;
    }

    private BookResponseDTO buildBookResponse(){
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setTitle("One Piece");
        bookResponseDTO.setAuthorName("Oda");
        return bookResponseDTO;
    }
}
