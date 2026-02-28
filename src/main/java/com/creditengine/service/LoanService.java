package com.creditengine.service;

import com.creditengine.dto.LoanApplyRequest;
import com.creditengine.dto.LoanApplicationDTO;
import com.creditengine.dto.LoanReviewRequest;
import com.creditengine.entity.Applicant;
import com.creditengine.entity.LoanApplication;
import com.creditengine.entity.LoanStatus;
import com.creditengine.exception.ResourceNotFoundException;
import com.creditengine.repository.LoanApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanService.class);

    private static final int MIN_CREDIT_SCORE = 700;
    private static final BigDecimal MIN_MONTHLY_INCOME = new BigDecimal("30000");
    private static final int INCOME_MULTIPLIER = 20;

    private final LoanApplicationRepository loanApplicationRepository;
    private final ApplicantService applicantService;

    public LoanService(LoanApplicationRepository loanApplicationRepository, ApplicantService applicantService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.applicantService = applicantService;
    }

    @Transactional
    public LoanApplicationDTO applyForLoan(LoanApplyRequest request) {
        log.info("Processing loan application for applicant id: {}", request.getApplicantId());
        Applicant applicant = applicantService.findApplicantById(request.getApplicantId());
        LoanStatus status = evaluateLoanApplication(applicant, request.getLoanAmount());
        LoanApplication loanApplication = LoanApplication.builder()
                .loanAmount(request.getLoanAmount())
                .tenureMonths(request.getTenureMonths())
                .interestRate(request.getInterestRate())
                .status(status)
                .applicant(applicant)
                .build();
        LoanApplication saved = loanApplicationRepository.save(loanApplication);
        log.info("Loan application {} created with status: {}", saved.getId(), status);
        return toDTO(saved);
    }

    @Transactional
    public LoanApplicationDTO reviewLoan(Long id, LoanReviewRequest request) {
        log.info("Reviewing loan application id: {} with status: {}", id, request.getStatus());
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + id));
        loanApplication.setStatus(request.getStatus());
        LoanApplication saved = loanApplicationRepository.save(loanApplication);
        log.info("Loan application {} updated to status: {}", id, request.getStatus());
        return toDTO(saved);
    }

    public LoanApplicationDTO getLoanById(Long id) {
        log.info("Fetching loan application with id: {}", id);
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + id));
        return toDTO(loanApplication);
    }

    public List<LoanApplicationDTO> getLoansByApplicantId(Long applicantId) {
        log.info("Fetching loan applications for applicant id: {}", applicantId);
        applicantService.findApplicantById(applicantId);
        List<LoanApplication> loans = loanApplicationRepository.findByApplicantId(applicantId);
        return loans.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private LoanStatus evaluateLoanApplication(Applicant applicant, BigDecimal loanAmount) {
        boolean approved = applicant.getCreditScore() >= MIN_CREDIT_SCORE
                && applicant.getMonthlyIncome().compareTo(MIN_MONTHLY_INCOME) >= 0
                && loanAmount.compareTo(applicant.getMonthlyIncome().multiply(BigDecimal.valueOf(INCOME_MULTIPLIER))) <= 0;
        return approved ? LoanStatus.APPROVED : LoanStatus.REJECTED;
    }

    private LoanApplicationDTO toDTO(LoanApplication loanApplication) {
        return LoanApplicationDTO.builder()
                .id(loanApplication.getId())
                .loanAmount(loanApplication.getLoanAmount())
                .tenureMonths(loanApplication.getTenureMonths())
                .interestRate(loanApplication.getInterestRate())
                .status(loanApplication.getStatus())
                .appliedDate(loanApplication.getAppliedDate())
                .applicantId(loanApplication.getApplicant().getId())
                .build();
    }
}
