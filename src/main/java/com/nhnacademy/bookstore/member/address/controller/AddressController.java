package com.nhnacademy.bookstore.member.address.controller;

import com.nhnacademy.bookstore.entity.address.Address;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.address.dto.request.AddressResponse;
import com.nhnacademy.bookstore.member.address.dto.request.CreateAddressRequest;
import com.nhnacademy.bookstore.member.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.bookstore.member.address.dto.response.UpdateAddressResponse;
import com.nhnacademy.bookstore.member.address.service.impl.AddressServiceImpl;
import com.nhnacademy.bookstore.member.member.service.impl.MemberServiceImpl;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * The type Address controller.
 *
 * @author 오연수, 유지아
 */
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final MemberServiceImpl memberService;
    private final AddressServiceImpl addressServiceImpl;

    /**
     * Create address response entity.
     * 주소만든다음 가지고있는 모든 주소를 반환한다.
     * @param request the request
     * @author 유지아
     * @return the response entity
     *
     */
    @PostMapping("/members/addresses")
    public ApiResponse<Set<AddressResponse>> createAddress(@RequestBody CreateAddressRequest request) {
        try{
            Member member = memberService.findById(request.memberId());
            Address address = new Address(request,member);
            addressServiceImpl.save(address);

            return ApiResponse.success(addressServiceImpl.findAll(member).stream().map(a-> AddressResponse.builder()
                    .name(a.getName()).country(a.getCountry()).city(a.getCity()).state(a.getState()).road(a.getRoad()).postalCode(a.getPostalCode())
                    .build()).collect(Collectors.toSet()));//이거 찾는거에서 오류 해주나? 이것두?
        }catch (RuntimeException e){
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        }

    }
    /**
     * Find all addresses response entity.
     * 멤버아이디에 따른 모든 주소를 가져온다.
     * @author 유지아
     * @param memberId the member id
     * @return the response entity
     */
//주소를 추가한다.
    @GetMapping("/members/addresses")
    public ApiResponse<Set<AddressResponse>> findAllAddresses(@RequestHeader("member-id") Long memberId) {
        try{
            Member member = memberService.findById(memberId);
            return ApiResponse.success(addressServiceImpl.findAll(member).stream().map(a->AddressResponse.builder()
                    .name(a.getName()).country(a.getCountry()).city(a.getCity()).state(a.getState()).road(a.getRoad()).postalCode(a.getPostalCode())
                    .build()).collect(Collectors.toSet()));
        }catch (RuntimeException e){
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        }
    }
    //멤버의 주소를 가져온다.

    /**
     * 주소 업데이트
     *
     * @param addressId            the address id
     * @param updateAddressRequest name, country, city, state, road, postalCode
     * @return the api response - UpdateAddressResponse DTO
     * @author 오연수
     */
    @PutMapping("/members/addresses")
    public ApiResponse<UpdateAddressResponse> updateAddress(@RequestHeader(name = "Address-Id") String addressId,
                                                            @RequestBody UpdateAddressRequest updateAddressRequest) {
        try {
            Address address = addressServiceImpl.updateAddress(addressId, updateAddressRequest);
            UpdateAddressResponse updateAddressResponse = UpdateAddressResponse.builder()
                    .id(addressId)
                    .name(address.getName())
                    .build();
            return ApiResponse.success(updateAddressResponse);
        }
        catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }


    /**
     * 주소 삭제
     *
     * @param addressId the address id
     * @return the api response - Void
     * @author 오연수
     */
    @DeleteMapping("/members/addresses")
    public ApiResponse<Void> deleteAddress(@RequestHeader(name = "Address-Id") String addressId) {
        try {
            addressServiceImpl.deleteAddress(addressId);
            return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.NO_CONTENT.value(), "Address deleted"));
        }
        catch (RuntimeException e) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
