package com.nhnacademy.front.purchase.admin.controller;

import com.nhnacademy.front.purchase.admin.dto.PointPolicyResponseRequest;
import com.nhnacademy.front.purchase.admin.service.AdminPointPolicyService;
import com.nhnacademy.front.purchase.admin.service.impl.AdminPointPolicyServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PointPolicyController {
    private final AdminPointPolicyService adminPointPolicyService;

    @GetMapping("/admin/points")
    public String createPointPolicyForm(){
        return "purchase/admin/point/policyForm";
    }

    @GetMapping("/admin/points/policies")
    public String getPointPolicyList(Model model){
        model.addAttribute("policies",adminPointPolicyService.readAll());
        return "purchase/admin/point/policyList";
    }


    @PostMapping("/admin/points/policies")
    public String createPointPolicy(@Valid @ModelAttribute PointPolicyResponseRequest pointPolicyResponseRequest,
                                    BindingResult bindingResult) {
        adminPointPolicyService.update(pointPolicyResponseRequest);
        return "redirect:/admin/purchases";
    }

    @PostMapping("/admin/points/policies/edit")
    public String updatePointPolic(@Valid @ModelAttribute PointPolicyResponseRequest pointPolicyResponseRequest,
                                    BindingResult bindingResult) {
        adminPointPolicyService.update(pointPolicyResponseRequest);
        return "redirect:/admin/purchases";
    }

    @GetMapping("/admin/points/policies/edit/{policyKey}")
    public String editPointPolicyForm(@PathVariable String policyKey, Model model) {
        model.addAttribute("pointPolicyResponseRequest", adminPointPolicyService.read(policyKey));
        return "purchase/admin/point/policyEditForm";
    }

}
