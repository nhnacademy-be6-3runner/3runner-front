package com.nhnacademy.front.member.member.dto.response;

import java.time.ZonedDateTime;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record GetMemberResponse(@NotNull @Size(min = 1, max = 50) String password,
								@NotNull Long point,
								@NotNull @Size(min = 1, max = 10) String name,
								int age,
								@NotNull @Size(min = 1, max = 11) String phone,
								@NotNull String email,
								ZonedDateTime birthday,
								@NotNull Grade grade,
								ZonedDateTime last_login_date,
								@NotNull ZonedDateTime created_at,
								ZonedDateTime modified_at){
}
