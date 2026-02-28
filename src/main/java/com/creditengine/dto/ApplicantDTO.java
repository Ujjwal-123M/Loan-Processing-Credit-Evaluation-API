package com.creditengine.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantDTO {

    private Long id;
    private String name;
    private String email;
    private BigDecimal monthlyIncome;
    private Integer creditScore;
    private LocalDateTime createdAt;
}
