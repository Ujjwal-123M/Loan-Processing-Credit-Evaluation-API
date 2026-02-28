package com.creditengine.service;

import com.creditengine.dto.ApplicantDTO;
import com.creditengine.dto.ApplicantRequest;
import com.creditengine.entity.Applicant;
import com.creditengine.exception.ResourceNotFoundException;
import com.creditengine.repository.ApplicantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicantService {

    private static final Logger log = LoggerFactory.getLogger(ApplicantService.class);

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Transactional
    public ApplicantDTO createApplicant(ApplicantRequest request) {
        log.info("Creating applicant with email: {}", request.getEmail());
        Applicant applicant = Applicant.builder()
                .name(request.getName())
                .email(request.getEmail())
                .monthlyIncome(request.getMonthlyIncome())
                .creditScore(request.getCreditScore())
                .build();
        Applicant saved = applicantRepository.save(applicant);
        log.info("Applicant created with id: {}", saved.getId());
        return toDTO(saved);
    }

    public ApplicantDTO getApplicantById(Long id) {
        log.info("Fetching applicant with id: {}", id);
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found with id: " + id));
        return toDTO(applicant);
    }

    public Applicant findApplicantById(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found with id: " + id));
    }

    private ApplicantDTO toDTO(Applicant applicant) {
        return ApplicantDTO.builder()
                .id(applicant.getId())
                .name(applicant.getName())
                .email(applicant.getEmail())
                .monthlyIncome(applicant.getMonthlyIncome())
                .creditScore(applicant.getCreditScore())
                .createdAt(applicant.getCreatedAt())
                .build();
    }
}
