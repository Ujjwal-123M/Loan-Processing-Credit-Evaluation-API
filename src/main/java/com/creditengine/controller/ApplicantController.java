package com.creditengine.controller;

import com.creditengine.dto.ApplicantDTO;
import com.creditengine.dto.ApplicantRequest;
import com.creditengine.service.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<ApplicantDTO> createApplicant(@Valid @RequestBody ApplicantRequest request) {
        ApplicantDTO applicant = applicantService.createApplicant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicantDTO> getApplicant(@PathVariable Long id) {
        ApplicantDTO applicant = applicantService.getApplicantById(id);
        return ResponseEntity.ok(applicant);
    }
}
