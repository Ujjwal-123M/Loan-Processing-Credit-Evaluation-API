package com.creditengine.dto;

import com.creditengine.entity.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplicationDTO {

    private Long id;
    private BigDecimal loanAmount;
    private Integer tenureMonths;
    private BigDecimal interestRate;
    private LoanStatus status;
    private LocalDateTime appliedDate;
    private Long applicantId;
}
