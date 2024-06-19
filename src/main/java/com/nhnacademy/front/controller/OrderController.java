package com.nhnacademy.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/order")
    public String order(){
        return "order";
    }

    @GetMapping("/order/complete")
    public String orderComplete(){
        return "order-complete";
    }
}
