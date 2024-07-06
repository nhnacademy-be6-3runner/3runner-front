package com.nhnacademy.front.purchase.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PointPolicyController {


    @GetMapping("/admin/points")
    public String createPointPolicyForm(){
        return "purchase/admin/point/policyForm";
    }

    @GetMapping("/admin/points/policies")
    public String getPointPolicyList(){
        return "purchase/admin/point/policyList";
    }


    @PostMapping("/admin/points/policies")
    public String createPointPolicy(){
        return "redirect:/purchase/admin/admin";
    }

    @PostMapping("/admin/points/policies/{policyId}")
    public String updatePointPolicy(@PathVariable Long policyId){
        return "redirect:/purchase/admin/admin";
    }

}
