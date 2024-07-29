package com.nhnacademy.front.purchase.admin.controller;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.purchase.admin.service.AdminCouponPolicyService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateBookCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCategoryCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateFixedCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateRatioCouponRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CouponPolicyController {

	private final AdminCouponPolicyService adminCouponPolicyService;

	@GetMapping("/admin/coupons/usages")
	public String usages(Model model) {
		model.addAttribute("usages", adminCouponPolicyService.getUsages());

		return "purchase/admin/coupon/usages";
	}

	@GetMapping("/admin/coupons/types")
	public String types(Model model) {
		model.addAttribute("types", adminCouponPolicyService.getTypes());

		return "purchase/admin/coupon/types";
	}

	@GetMapping("/admin/coupons/usages/form")
	public String usageForm(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
		Model model) {
		Page<BookListResponse> bookPage = adminCouponPolicyService.getBooks(size, page, null);
		model.addAttribute("categories", adminCouponPolicyService.getCategories());
		model.addAttribute("books", bookPage);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("totalPages", bookPage.getTotalPages());

		return "purchase/admin/coupon/usageForm";
	}

	@GetMapping("/admin/coupons/types/form")
	public String typeForm() {
		return "purchase/admin/coupon/typeForm";
	}

	@PostMapping("/admin/coupons/usages/categories")
	public String createCategoryUsages(@Valid @ModelAttribute CreateCategoryCouponRequest createCategoryCouponRequest,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "purchase/admin/coupon/usageForm";
		}
		adminCouponPolicyService.createCategoryUsages(createCategoryCouponRequest);

		return "redirect:/admin/purchases";
	}

	@PostMapping("/admin/coupons/usages/books")
	public String createBookUsages(@Valid @ModelAttribute CreateBookCouponRequest createBookCouponRequest,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "purchase/admin/coupon/usageForm";
		}
		adminCouponPolicyService.createBookUsages(createBookCouponRequest);

		return "redirect:/admin/purchases";
	}

	@PostMapping("/admin/coupons/types/ratios")
	public String createRatioTypes(@Valid @ModelAttribute CreateRatioCouponRequest createRatioCouponRequest,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "purchase/admin/coupon/types";
		}
		adminCouponPolicyService.createRatioTypes(createRatioCouponRequest);

		return "redirect:/admin/purchases";
	}

	@PostMapping("/admin/coupons/types/fixes")
	public String createFixedTypes(@ModelAttribute CreateFixedCouponRequest createFixedCouponRequest,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "purchase/admin/coupon/types";
		}
		adminCouponPolicyService.createFixedTypes(createFixedCouponRequest);

		return "redirect:/admin/purchases";
	}
}
