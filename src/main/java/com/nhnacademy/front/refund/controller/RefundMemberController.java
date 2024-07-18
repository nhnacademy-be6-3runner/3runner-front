package com.nhnacademy.front.refund.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.feign.RefundRecordMemberControllerClient;
import com.nhnacademy.front.refund.service.RefundRecordMemberService;
import com.nhnacademy.front.refund.service.RefundService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 환불 컨트롤러(회원)
 *
 * @author 정주혁
 *
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/refund/members")
public class RefundMemberController {

	private final RefundService refundService;
	private final RefundRecordMemberService refundRecordMemberService;


	/**
	 * 환불 페이지 이동
	 *
	 * @param purchaseId 주문 id
	 * @param type 결제 취소, 환불 구분
	 * @param model
	 * @return
	 */
	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable(name = "orderNumber") Long purchaseId, @RequestParam(name = "type") String type
		, Model model) {

		model.addAttribute("type", type);
		List<ReadPurchaseBookResponse> books = refundService.readMemberPurchaseBooks(purchaseId);

		model.addAttribute("books", books);

		return "refund";
	}

	/**
	 * 결제 취소
	 *
	 * @param orderNumber 주문 id
	 * @param price 가격
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("/cancelPayment/{orderNumber}")
	public ResponseEntity<Map<String, String>> cancelPayment(@PathVariable(name = "orderNumber") Long orderNumber,
		@RequestParam(name = "refund-price", required = false) Integer price, Model model) {

		Map<String, Object> result = refundService.refundToss(orderNumber, price, "결제 취소");

		Boolean isSuccess = (Boolean)result.get("isSuccess");
		JSONObject json = (JSONObject)result.get("jsonObject");

		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("jsonObject", json);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Refund request successful");
		response.put("redirectUrl", "/orders/members");

		return ResponseEntity.ok(response);
	}

	/**
	 * 환불 요청
	 *
	 * @param purchaseId 주문 id
	 * @param refundContent 환불 사유
	 * @param price 환불 가격
	 * @return
	 */
	@ResponseBody
	@PostMapping("/{purchaseId}")
	public ResponseEntity<Map<String, String>> refundRequest(@PathVariable(name = "purchaseId") Long purchaseId,

		@RequestParam(name = "refund-content") String refundContent,
		@RequestParam(name = "refund-price") Integer price) {
		Long refundId = refundService.createRefundRequest(purchaseId, price, refundContent);
		if (Boolean.FALSE.equals(
			refundRecordMemberService.createRefundRecordMember(purchaseId, refundId))) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("message", "환불 요청이 완료된 건입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

		Map<String, String> response = new HashMap<>();
		response.put("message", "Refund request successful");
		response.put("redirectUrl", "/refund/members/success");

		return ResponseEntity.ok(response);
	}

	/**
	 * 임시저장된 환불 내역 수정
	 *
	 * @param orderNumber
	 * @param purchaseBookId
	 * @param quantity
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/{orderNumber}/update/{purchaseBookId}")
	public void updateRefund(@PathVariable(name = "orderNumber") Long orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestParam(name = "quantity", required = false) Integer quantity, HttpServletResponse response) throws
		IOException {
		refundRecordMemberService.updateRefundRecorderMember(orderNumber, purchaseBookId, quantity);
		response.getWriter().write("");
	}

	/**
	 * 임시저장된 환불 내역 모두 수정(최댓값)
	 *
	 * @param orderNumber
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/{orderNumber}/update/all")
	public void updateRefundAll(@PathVariable(name = "orderNumber") Long orderNumber, HttpServletResponse response) throws
		IOException {
		refundRecordMemberService.updateRefundAll(orderNumber);
		response.getWriter().write("");
	}

	/**
	 * 임시저장된 환불 내역 모두 수정(0)
	 *
	 * @param orderNumber
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/{orderNumber}/update/all/zero")
	public void updateRefundAllZero(@PathVariable(name = "orderNumber") Long orderNumber, HttpServletResponse response) throws
		IOException {
		refundRecordMemberService.updateRefundAllZero(orderNumber);
		response.getWriter().write("");
	}

	/**
	 * 성공 페이지로 이동
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/success")
	public String success(Model model) {
		model.addAttribute("result", "success");
		return "refundSuccess";
	}
}
