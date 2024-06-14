package com.nhnacademy.bookstore.member.address.repository;

import com.nhnacademy.bookstore.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
