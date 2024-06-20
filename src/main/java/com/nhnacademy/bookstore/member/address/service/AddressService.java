package com.nhnacademy.bookstore.member.address.service;

import com.nhnacademy.bookstore.entity.address.Address;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.address.dto.request.UpdateAddressRequest;

import java.util.List;

public interface AddressService {
    void save(Address address);

    List<Address> readAll(Member member);

    Address updateAddress(String addressId, UpdateAddressRequest updateAddressRequest);

    void deleteAddress(String addressId);
}
