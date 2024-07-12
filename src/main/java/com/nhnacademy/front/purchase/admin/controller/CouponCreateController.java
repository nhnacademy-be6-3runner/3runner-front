package com.nhnacademy.front.purchase.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.purchase.admin.dto.CreateCouponFormFrontRequest;
import com.nhnacademy.front.purchase.admin.dto.CreateCouponFormFrontRequestWithMq;
import com.nhnacademy.front.purchase.admin.service.AdminCouponCreateService;
import com.nhnacademy.front.purchase.admin.service.CouponRegisterService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCouponFormRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class CouponCreateController {
    private final AdminCouponCreateService adminCouponCreateService;
    private final CouponRegisterService couponRegisterService;

    @GetMapping("/admin/coupons")
    public String createCouponForm(Model model){
        model.addAttribute("types", adminCouponCreateService.getTypes());
        model.addAttribute("usages", adminCouponCreateService.getUsages());
        model.addAttribute("members", adminCouponCreateService.getMembers());
        model.addAttribute("createCouponFormFrontRequest", new CreateCouponFormFrontRequest());
        return "purchase/admin/coupon/couponForm";
    }
    @GetMapping("/admin/coupons/mqs")
    public String createCouponFormMq(Model model){
        model.addAttribute("types", adminCouponCreateService.getTypes());
        model.addAttribute("usages", adminCouponCreateService.getUsages());
        model.addAttribute("createCouponFormFrontRequest", new CreateCouponFormFrontRequest());
        return "purchase/admin/coupon/couponFormMq";
    }

    @GetMapping("/admin/coupons/all")
    public String getCouponList(Model model){

        model.addAttribute("coupons", adminCouponCreateService.getAllCouponForm());

        return "purchase/admin/coupon/couponList";
    }

    @PostMapping("/admin/coupons")
    public String createCoupon(@Valid @ModelAttribute CreateCouponFormFrontRequest createCouponFormFrontRequest,
                               BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("types", adminCouponCreateService.getTypes());
            model.addAttribute("usages", adminCouponCreateService.getUsages());
            model.addAttribute("members", adminCouponCreateService.getMembers());

            return "purchase/admin/coupon/couponForm";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime startDate = ZonedDateTime.parse(createCouponFormFrontRequest.getStartDate(), formatter.withZone(java.time.ZoneId.systemDefault()));
        ZonedDateTime endDate = ZonedDateTime.parse(createCouponFormFrontRequest.getEndDate(), formatter.withZone(java.time.ZoneId.systemDefault()));

        adminCouponCreateService.createCouponForm(CreateCouponFormRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .name(createCouponFormFrontRequest.getName())
                .maxPrice(createCouponFormFrontRequest.getMaxPrice())
                .minPrice(createCouponFormFrontRequest.getMinPrice())
                .couponTypeId(createCouponFormFrontRequest.getCouponTypeId())
                .couponUsageId(createCouponFormFrontRequest.getCouponUsageId()).build(),
                createCouponFormFrontRequest.getMemberId()
        );

        return "redirect:/admin/coupons/all";
    }

    @PostMapping("/admin/coupons/mqs")
    public String createCouponMq(@Valid @ModelAttribute CreateCouponFormFrontRequestWithMq createCouponFormFrontRequest,
                               BindingResult bindingResult,
                               Model model) throws JsonProcessingException {
        if(bindingResult.hasErrors()){
            model.addAttribute("types", adminCouponCreateService.getTypes());
            model.addAttribute("usages", adminCouponCreateService.getUsages());

            return "purchase/admin/coupon/couponFormMq";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime startDate = ZonedDateTime.parse(createCouponFormFrontRequest.getStartDate(), formatter.withZone(java.time.ZoneId.systemDefault()));
        ZonedDateTime endDate = ZonedDateTime.parse(createCouponFormFrontRequest.getEndDate(), formatter.withZone(java.time.ZoneId.systemDefault()));

        couponRegisterService.creatWithMq(CreateCouponFormRequest.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .name(createCouponFormFrontRequest.getName())
                        .maxPrice(createCouponFormFrontRequest.getMaxPrice())
                        .minPrice(createCouponFormFrontRequest.getMinPrice())
                        .couponTypeId(createCouponFormFrontRequest.getCouponTypeId())
                        .couponUsageId(createCouponFormFrontRequest.getCouponUsageId()).build(),
                createCouponFormFrontRequest.getQuantity());

        return "redirect:/admin/purchases";
    }


    //어드민 페이지
    @GetMapping("/admin/purchases")
    public String admin(){
        return "purchase/admin/admin";
    }
}
