package com.nhnacademy.front.refund.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateRefundRequest(@NotNull @Size(max = 200) String refundContent, @Min(0) Integer price) {
}
