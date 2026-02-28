package com.creditengine.controller;

import com.creditengine.dto.LoanApplicationDTO;
import com.creditengine.dto.LoanApplyRequest;
import com.creditengine.dto.LoanReviewRequest;
import com.creditengine.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationDTO> applyForLoan(@Valid @RequestBody LoanApplyRequest request) {
        LoanApplicationDTO loan = loanService.applyForLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<LoanApplicationDTO> reviewLoan(@PathVariable Long id, @Valid @RequestBody LoanReviewRequest request) {
        LoanApplicationDTO loan = loanService.reviewLoan(id, request);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationDTO> getLoan(@PathVariable Long id) {
        LoanApplicationDTO loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<LoanApplicationDTO>> getLoansByApplicant(@PathVariable Long applicantId) {
        List<LoanApplicationDTO> loans = loanService.getLoansByApplicantId(applicantId);
        return ResponseEntity.ok(loans);
    }
}
