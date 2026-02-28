package com.creditengine.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplyRequest {

    @NotNull(message = "Applicant ID is required")
    private Long applicantId;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal loanAmount;

    @NotNull(message = "Tenure is required")
    @Min(1)
    @Max(360)
    private Integer tenureMonths;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0", inclusive = true)
    @DecimalMax(value = "100", inclusive = true)
    private BigDecimal interestRate;
}
