package com.nhnacademy.bookstore.member.pointRecord.controller;

import com.nhnacademy.bookstore.member.pointRecord.dto.response.ReadPointRecordResponse;
import com.nhnacademy.bookstore.member.pointRecord.service.PointRecordService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 포인트레코드 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
public class PointRecordController {
    private final PointRecordService pointRecordService;

    /**
     * 맴버의 포인트 내역 출력
     *
     * @param memberId 맴버아이디
     * @return 포인트내역Dto리스트
     */
    @GetMapping("/bookstore/members/points")
    public ApiResponse<List<ReadPointRecordResponse>> readPointRecord(
            @RequestHeader Long memberId
    ) {
        return ApiResponse.success(pointRecordService.readByMemberId(memberId));

    }
}
