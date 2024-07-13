package com.nhnacademy.front.purchase.mypage.service;

import com.nhnacademy.front.purchase.mypage.dto.request.UpdateMemberMessageRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadMemberMessageResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface MemberMessageService {
    Page<ReadMemberMessageResponse> readAllById(int page, int size);

    Long readUnreadedMessage();

    Void updateMessage(UpdateMemberMessageRequest updateMemberMessageRequest);
}
