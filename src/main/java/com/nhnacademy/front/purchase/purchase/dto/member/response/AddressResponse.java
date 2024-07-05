package com.nhnacademy.front.purchase.purchase.dto.member.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AddressResponse(Long addressId,
                                String name,
                              String country,
                              String city,
                              String state,
                              String road,
                              String postalCode){

}

