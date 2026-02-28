package com.creditengine.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Monthly income is required")
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal monthlyIncome;

    @NotNull(message = "Credit score is required")
    @Min(300)
    @Max(850)
    private Integer creditScore;
}
