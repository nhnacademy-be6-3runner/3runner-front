package com.nhnacademy.front.refund.controller;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/refund")
public class RefundManagerController {

	private final RefundService refundService;


	@GetMapping("/detail/{refundId}")
	public String detail(@PathVariable(name = "refundId") Long refundId, Model model) {

		ReadRefundResponse response = refundService.readRefund(refundId);
		model.addAttribute("refund",response);
		return "refund-request-detail";
	}

	@PostMapping("/success/{refundId}")
	public String success(@PathVariable(name = "refundId") Long refundId, Model model) {
		refundService.updateRefundSuccess(refundId);
		return "redirect:/admin/refund/all";
	}


	@PostMapping("/reject/{refundId}")
	public String reject(@PathVariable(name = "refundId") Long refundId, Model model) {
		refundService.updateRefundReject(refundId);
		return "redirect:/admin/refund/all";
	}

	@GetMapping("/all")
	public String all(Model model) {
		model.addAttribute("refunds",refundService.readRefundAll());
		return "admin-refund";
	}

}
