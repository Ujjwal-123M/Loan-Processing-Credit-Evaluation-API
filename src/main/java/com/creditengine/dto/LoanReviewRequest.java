package com.creditengine.dto;

import com.creditengine.entity.LoanStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanReviewRequest {

    @NotNull(message = "Status is required")
    private LoanStatus status;
}
