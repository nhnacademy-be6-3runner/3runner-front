package com.nhnacademy.bookstore.entity.address;

import com.nhnacademy.bookstore.entity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Member member;

    @Size(min = 1, max = 20)
    @NotNull
    private String name;
    @Size(min = 1, max = 100)
    @NotNull
    private String country;
    @Size(min = 1, max = 100)
    @NotNull
    private String city;
    @Size(min = 1, max = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String state;
    @NotNull
    @Size(min = 1, max = 100)
    private String road;
    @Size(min = 1, max = 20)
    @NotNull
    private String postal_code;

}
