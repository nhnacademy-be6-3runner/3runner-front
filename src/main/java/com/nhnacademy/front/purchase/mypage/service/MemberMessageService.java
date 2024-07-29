package com.nhnacademy.front.purchase.mypage.service;

import com.nhnacademy.front.purchase.mypage.dto.request.UpdateMemberMessageRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadMemberMessageResponse;
import org.springframework.data.domain.Page;

public interface MemberMessageService {
	Page<ReadMemberMessageResponse> readAllById(int page, int size);

	Long readUnReadedMessage();

	Void updateMessage(UpdateMemberMessageRequest updateMemberMessageRequest);
}
