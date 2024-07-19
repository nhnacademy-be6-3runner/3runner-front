package com.nhnacademy.front.refund.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.refund.dto.response.ReadRefundResponse;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

/**
 * 관리자 환불 관리 컨트롤러
 *
 * @author 정주혁
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/refund")
public class RefundManagerController {

	private final RefundService refundService;

	/**
	 * 환불 수락
	 *
	 * @param refundId
	 * @param model
	 * @return
	 */
	@PostMapping("/success/{refundId}")
	@ResponseBody
	public ResponseEntity<String> success(@PathVariable(name = "refundId") Long refundId, Model model) {
		refundService.updateRefundSuccess(refundId);

		return ResponseEntity.ok("success");
	}

	/**
	 * 환불 거절
	 *
	 * @param refundId
	 * @param model
	 * @return
	 */
	@PostMapping("/reject/{refundId}")
	@ResponseBody
	public ResponseEntity<String> reject(@PathVariable(name = "refundId") Long refundId, Model model) {
		refundService.updateRefundReject(refundId);
		return ResponseEntity.ok("reject");
	}

	/**
	 * 모든 환불 조회
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/all")
	public String all(Model model) {
		model.addAttribute("refunds",refundService.readRefundAll());
		return "refund/admin/admin-refund";
	}

}
