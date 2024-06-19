package com.nhnacademy.bookstore.entity.member;


import com.nhnacademy.bookstore.entity.address.Address;
import com.nhnacademy.bookstore.entity.member.enums.Grade;
import com.nhnacademy.bookstore.entity.member.enums.Status;
import com.nhnacademy.bookstore.entity.memberAuth.MemberAuth;
import com.nhnacademy.bookstore.entity.pointRecord.PointRecord;
import com.nhnacademy.bookstore.member.member.dto.request.CreateMemberRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String password;

    @NotNull
    private Long point;

    @NotNull
    @Size(min = 1, max = 10)
    private String name;

    private int age;

    @NotNull
    @Size(min = 1, max = 11)
    private String phone;

    @NotNull
    @Column(unique = true)
    private String email;

    private ZonedDateTime birthday;

    @NotNull
    private Grade grade;

    @NotNull
    private Status status;

    private ZonedDateTime last_login_date;

    @NotNull
    private ZonedDateTime created_at;

    private ZonedDateTime modified_at;
    private ZonedDateTime deleted_at;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Address> addressSet = new HashSet<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<MemberAuth> memberAuthSet = new HashSet<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<PointRecord> pointSet = new HashSet<>();

    public Member(CreateMemberRequest request){
        this.setPassword(request.password());
        this.setPoint(5000L);
        this.setName(request.name());
        this.setAge(request.age());
        this.setStatus(Status.Active);
        this.setPhone(request.phone());
        this.setEmail(request.email());
        this.setBirthday(request.birthday());
        this.setGrade(Grade.General);
        this.setCreated_at(ZonedDateTime.now());
    }

}
