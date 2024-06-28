package com.nhnacademy.front.purchase.purchase.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Objects;

/**
 * 주문 UI 컨트롤러.
 *
 * @author 김병우
 */
@Controller
public class PurchaseController {

    /**
     * 비회원, 회원 판별 주문.
     *
     * @param cartId 카트아이디
     * @param memberId 맴버아이디
     * @param response HTTP RESPONSE
     * @throws IOException
     */
    @PostMapping("/api/purchases")
    public void purchase(
            @RequestParam("cartId") String cartId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            HttpServletResponse response
    ) throws IOException {

        if (Objects.isNull(memberId)) {
            response.sendRedirect("/api/purchases/guests/"+cartId);
        } else {
            response.sendRedirect("/api/purchases/members");
        }
    }
}
