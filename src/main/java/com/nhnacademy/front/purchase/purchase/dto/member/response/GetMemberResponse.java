package com.nhnacademy.front.purchase.purchase.dto.member.response;

import com.nhnacademy.front.purchase.purchase.dto.member.Grade;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record GetMemberResponse(@NotNull @Size(min = 1, max = 50) String password,
								@NotNull Long point,
								@NotNull @Size(min = 1, max = 10) String name,
								int age,
								@NotNull @Size(min = 1, max = 11) String phone,
								@NotNull @Column(unique = true) String email,
								ZonedDateTime birthday,
								@NotNull Grade grade,
								ZonedDateTime lastLoginDate,
								@NotNull ZonedDateTime createdAt,
								ZonedDateTime modifiedAt) {
}
