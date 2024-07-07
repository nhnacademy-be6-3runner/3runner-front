package com.nhnacademy.front.purchase.admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponFormFrontRequest {
    @NotNull
    private Long memberId;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private Integer maxPrice;

    @NotNull
    @Min(0)
    private Integer minPrice;

    @NotNull
    private Long couponTypeId;

    @NotNull
    private Long couponUsageId;

}