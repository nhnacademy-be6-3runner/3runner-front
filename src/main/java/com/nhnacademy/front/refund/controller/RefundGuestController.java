package com.nhnacademy.front.refund.controller;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.service.RefundRecordGuestService;
import com.nhnacademy.front.refund.service.RefundService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 환불 컨트롤러(비회원).
 *
 * @author 정주혁
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/refund/guests")
public class RefundGuestController {
	private final RefundService refundService;
	private final RefundRecordGuestService refundRecordGuestService;

	/**
	 * 환불 페이지 이동
	 *
	 * @param orderNumber 환불할 주문 orderNumber
	 * @param model
	 * @return
	 */
	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable String orderNumber, Model model) {
		List<ReadPurchaseBookResponse> books = refundService.readGuestPurchaseBooks(orderNumber);
		model.addAttribute("orderNumber", orderNumber);
		model.addAttribute("books", books);
		return "refundGuest";
	}

	/**
	 * 임시저장된 환불내역 수정
	 *
	 * @param orderNumber
	 * @param purchaseBookId
	 * @param quantity
	 */
	@GetMapping("/{orderNumber}/update/{purchaseBookId}")
	public void updateRefund(@PathVariable(name = "orderNumber") String orderNumber,
		@PathVariable(name = "purchaseBookId") Long purchaseBookId,
		@RequestParam(name = "quantity", required = false) Integer quantity, HttpServletResponse response)
		throws IOException {
		refundRecordGuestService.updateRefundRecorderGuest(orderNumber, purchaseBookId, quantity);
		response.getWriter().write("");
	}

	/**
	 * 임시 저장된 환불 내역 모두 수정(최고값)
	 *
	 * @param orderNumber
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/{orderNumber}/update/all")
	public void updateRefundAll(@PathVariable(name = "orderNumber") String orderNumber,
		HttpServletResponse response) throws
		IOException {
		refundRecordGuestService.updateRefundGuestAll(orderNumber);
		response.getWriter().write("");
	}

	/**
	 * 임시 저장된 환불 내역 모두 수정(0)
	 *
	 * @param orderNumber
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/{orderNumber}/update/all/zero")
	public void updateRefundAllZero(@PathVariable(name = "orderNumber") String orderNumber,
		HttpServletResponse response) throws
		IOException {
		refundRecordGuestService.updateRefundRecordAllZero(orderNumber);
		response.getWriter().write("");

	}

	/**
	 * 결제 취소
	 *
	 * @param orderNumber
	 * @param price
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("/cancelPayment/{orderNumber}")
	public ResponseEntity<Map<String, String>> cancelPayment(@PathVariable(name = "orderNumber") String orderNumber
		, @RequestParam(name = "refund-price", required = false) Integer price
		, Model model) {

		Map<String, Object> result = refundService.refundToss(orderNumber, price, "결제 취소");

		JSONObject json = (JSONObject)result.get("jsonObject");

		Map<String, String> response = new HashMap<>();
		response.put("message", "Refund request successful");
		response.put("redirectUrl", "/");

		return ResponseEntity.ok(response);
	}

}
