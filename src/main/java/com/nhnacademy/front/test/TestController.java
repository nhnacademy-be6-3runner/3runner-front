package com.nhnacademy.front.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore")
public class TestController {
    @Autowired
    private Adapter testAdapter;

    @GetMapping("/book")
    public ResponseEntity<String> getBooks() {
        String str = testAdapter.getBook();
        return ResponseEntity.ok(str);
    }

    @GetMapping("/coupon")
    public ResponseEntity<String> getCoupon() {
        String str = testAdapter.getCoupon();
        return ResponseEntity.ok(str);
    }


}
