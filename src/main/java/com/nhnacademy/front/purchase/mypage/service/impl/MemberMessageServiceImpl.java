package com.nhnacademy.front.purchase.mypage.service.impl;

import com.nhnacademy.front.purchase.mypage.dto.request.UpdateMemberMessageRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadMemberMessageResponse;
import com.nhnacademy.front.purchase.mypage.feign.MemberMessageControllerClient;
import com.nhnacademy.front.purchase.mypage.service.MemberMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberMessageServiceImpl implements MemberMessageService {
	private final MemberMessageControllerClient memberMessageControllerClient;

	@Override
	public Page<ReadMemberMessageResponse> readAllById(int page, int size) {
		return memberMessageControllerClient.readAllById(page, size).getBody().getData();
	}

	@Override
	public Long readUnReadedMessage() {
		return memberMessageControllerClient.readUnReadMessage().getBody().getData();
	}

	@Override
	public Void updateMessage(UpdateMemberMessageRequest updateMemberMessageRequest) {
		return memberMessageControllerClient.updateMessage(updateMemberMessageRequest).getBody().getData();
	}
}
