package com.nhnacademy.front.member.member.controller;

import com.nhnacademy.front.auth.adapter.DormantAdapter;
import com.nhnacademy.front.auth.dto.request.DormantCodeRequest;
import com.nhnacademy.front.auth.dto.request.DormantRequest;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;
import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.PasswordCorrectRequest;
import com.nhnacademy.front.member.member.dto.request.UpdatePasswordRequest;
import com.nhnacademy.front.member.member.dto.response.DormantResponse;
import com.nhnacademy.front.member.member.dto.response.UpdateMemberResponse;
import com.nhnacademy.front.member.member.feign.MemberControllerClient;
import com.nhnacademy.front.purchase.purchase.dto.member.request.UpdateMemberRequest;
import com.nhnacademy.front.purchase.purchase.dto.member.response.GetMemberResponse;
import com.nhnacademy.front.util.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class MemberRestController {
    private final MemberControllerClient memberControllerClient;
    private final AddressControllerClient addressControllerClient;
    private final LoginService loginService;
    private final DormantAdapter dormantAdapter;

    @DeleteMapping("/member")
    public Boolean deleteMember(HttpServletRequest request, HttpServletResponse response) {
        ApiResponse<Void> result = memberControllerClient.deleteMember();

        try {
            loginService.logout();
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Access")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                if (cookie.getName().equals("Refresh")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return result.getHeader().isSuccessful();
    }
    @PutMapping("/member")
    public Boolean updateMember(@Valid @RequestBody UpdateMemberRequest updateMemberRequest) {
        ApiResponse<UpdateMemberResponse> response = memberControllerClient.updateMembers(updateMemberRequest);
        return response.getHeader().isSuccessful();
    }

    @GetMapping("/member/email")
    public ApiResponse<Boolean> checkEmailExists(@RequestParam String email) {
        return memberControllerClient.emailExists(email);
    }
    @PutMapping("/member/password")
    public Boolean updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        ApiResponse<Void> response = memberControllerClient.updatePassword(updatePasswordRequest);
        return response.getHeader().isSuccessful();
    }
    @PostMapping("/member/password")
    public Boolean isPasswordMatch(@RequestBody PasswordCorrectRequest passwordCorrectRequest) {
        ApiResponse<Void> response = memberControllerClient.isPasswordMatch(passwordCorrectRequest);
        return response.getHeader().isSuccessful();
    }
    @GetMapping("/member/dormant/resend")
    public boolean resendDormant(HttpSession session) {
        String email = session.getAttribute("email").toString();
        ApiResponse<Void> response = dormantAdapter.resendDormant(email);
        return response.getHeader().isSuccessful();
    }
    @PostMapping("/member/dormant")
    public boolean dormant(@RequestBody DormantCodeRequest code, HttpSession session,
                           HttpServletResponse servletResponse) {
        ApiResponse<DormantResponse> response = dormantAdapter.dormantCheck(
                DormantRequest.builder().email(session.getAttribute("email").toString()).code(code.code()).build());
        DormantResponse dormantResponse = response.getBody().getData();
        if (response.getHeader().isSuccessful()) {
            String access = dormantResponse.access();

            String refresh = dormantResponse.refresh();

            Cookie cookie1 = new Cookie("Access", access);
            cookie1.setPath("/");
            servletResponse.addCookie(cookie1);
            Cookie cookie2 = new Cookie("Refresh", refresh);
            cookie2.setPath("/");
            servletResponse.addCookie(cookie2);

            session.removeAttribute("email");
            return true;
        } else {
            return false;
        }

    }
}
