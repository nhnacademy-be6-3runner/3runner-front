package com.nhnacademy.front.purchase.purchase.dto.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The type Create address request. -주소 추가에 대한 요청 record 이다.
 *
 * @author 유지아
 */
@Builder
public record CreateAddressRequest(
	@NotNull String name,
	@NotNull String country,
	@NotNull String city,
	@NotNull String state,
	@NotNull String road,
	@NotNull String postalCode
) {
}
