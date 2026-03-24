package com.example.libraryapidocker.controller;

import com.example.libraryapidocker.dto.request.LoanRequestDTO;
import com.example.libraryapidocker.dto.response.LoanResponseDTO;
import com.example.libraryapidocker.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @GetMapping
    @Operation(summary = "Retreive all loans.")
    public List<LoanResponseDTO> getAllLoans(){
        return loanService.getAllLoans();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retreive loan by user id.")
    public List<LoanResponseDTO> getLoansByUserId(@PathVariable Long userId){
        return loanService.getLoanByUserId(userId);
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity returnLoan(@PathVariable Long id){
        loanService.returnLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<LoanResponseDTO> saveLoan(@Valid @RequestBody LoanRequestDTO loanRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(loanRequestDTO));
    }

}
