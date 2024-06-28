package com.nhnacademy.front.token.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.token.adapter.TokenAdapter;
import com.nhnacademy.front.token.dto.request.RefreshRequest;
import com.nhnacademy.front.token.dto.response.RefreshResponse;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.ApiResponse;

@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenAdapter tokenAdapter;

	@Override
	public String requestNewAccessToken(String refreshToken) {
		ApiResponse<RefreshResponse> response = tokenAdapter.requestNewAccessToken(new RefreshRequest(refreshToken));
		return response.getBody().getData().accessToken();
	}
}
