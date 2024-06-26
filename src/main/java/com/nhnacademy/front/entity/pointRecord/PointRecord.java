package com.nhnacademy.front.entity.pointRecord;

import com.nhnacademy.front.entity.member.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointRecord {

    private Long id;

    @NotNull
    private Long member_point;
    @NotNull
    private Long remnant_point;
    @NotNull
    private ZonedDateTime created_at;
    @NotNull
    @Size(min = 1, max = 100)
    private String content;

    @NotNull
    private Member member;
}
