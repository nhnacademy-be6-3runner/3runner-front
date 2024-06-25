package com.nhnacademy.bookstore.member.member.controller;


import com.nhnacademy.bookstore.entity.auth.Auth;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.pointRecord.PointRecord;
import com.nhnacademy.bookstore.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.bookstore.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.bookstore.member.member.dto.response.GetMemberResponse;
import com.nhnacademy.bookstore.member.member.dto.response.UpdateMemberResponse;
import com.nhnacademy.bookstore.member.auth.service.AuthService;
import com.nhnacademy.bookstore.member.memberAuth.service.MemberAuthService;
import com.nhnacademy.bookstore.member.pointRecord.service.PointService;
import com.nhnacademy.bookstore.member.member.service.impl.MemberServiceImpl;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * The type Member controller.
 *
 * @author 오연수, 유지아
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;
    private final PointService pointRecordService;
    private final AuthService authService;
    private final MemberAuthService memberAuthService;


    /**
     * Create member response entity.- 회원가입에 사용되는 함수이다.
     *
     * @param request the request - creatememberrequest를 받아 member를 생성한다.
     * @author 유지아
     */
    @PostMapping("/members")
    public ApiResponse<Void> createMember(@RequestBody CreateMemberRequest request) {
                Member member = new Member(request);
                Auth auth = authService.getAuth("USER");

                PointRecord pointRecord = new PointRecord(null, 5000L, 5000L, ZonedDateTime.now(), "회원가입 5000포인트 적립.", member);
                pointRecordService.save(pointRecord);
                memberAuthService.saveAuth(member, auth);
                memberService.save(member);
            return ApiResponse.success(null);
    }

    /**
     * Find by member id response entity. -멤버아이디를 기반으로 멤버정보를 가져온다.
     *
     * @param memberId the member id -long형인 memberId값을 request header로 받는다.
     * @return the response entity -멤버 정보에 대한 응답을 담아서 apiresponse로 응답한다.
     * @author 유지아
     */
    @GetMapping("/members")
    public ApiResponse<GetMemberResponse> findById(@RequestHeader("member-id") Long memberId) {
            Member member = memberService.findById(memberId);
            GetMemberResponse getMemberResponse = GetMemberResponse.builder()
                    .age(member.getAge())
                    .grade(member.getGrade())
                    .point(member.getPoint())
                    .phone(member.getPhone())
                    .created_at(member.getCreated_at())
                    .birthday(member.getBirthday())
                    .email(member.getEmail())
                    .name(member.getName())
                    .password(member.getPassword()).build();

            return ApiResponse.success(getMemberResponse);
    }

    /**
     * Find by email and password response entity. -이메일과 비밀번호에 맞는 멤버정보를 반환한다.
     *
     * @param email    the email -이메일값을 문자열로 받는다.
     * @param password the password -비밀번호값을 문자열로 받는다.
     * @return the response entity -멤버 정보에 대한 응답을 담아서 apiresponse로 응답한다.
     * @author 유지아
     */
    @GetMapping("/members/login")
    public ResponseEntity<Member> findByEmailAndPassword(
            @RequestHeader("email") String email,
            @RequestHeader("password") String password) {
        return ResponseEntity.ok(memberService.findByEmailAndPassword(email, password));
    }

    /**
     * Find auths list. -권한에 대한 리스트를 받아온다.
     *
     * @param memberId the member id -멤버아이디를 받는다.
     * @return the list -해당 유저에 대한 권한들을 응답에 담아 apiresponse로 응답한다.
     * @author 유지아
     */
    @GetMapping("/members/auths")
    public List<Auth> findAuths(@RequestHeader("member-id")Long memberId){
        return memberAuthService.findAllAuths(memberId);
    }

    /**
     * 멤버 업데이트
     *
     * @param memberId            멤버 id
     * @param updateMemberRequest password, name, age, phone, email, birthday
     * @return the api response - updateMemberResponse
     * @author 오연수
     */
    @PutMapping("/members")
    public ApiResponse<UpdateMemberResponse> updateMember(@RequestHeader(name = "Member-Id") String memberId,
                                                          @Valid @RequestBody UpdateMemberRequest updateMemberRequest) {

            Member updatedMember = memberService.updateMember(memberId, updateMemberRequest);
            UpdateMemberResponse updateMemberResponse = UpdateMemberResponse.builder()
                    .id(String.valueOf(updatedMember.getId()))
                    .name(updatedMember.getName()).build();
            return ApiResponse.success(updateMemberResponse);
    }

    /**
     * 멤버 탈퇴 처리
     *
     * @param memberId 멤버 id
     * @return the api response - Void
     * @author 오연수
     */
    @DeleteMapping("/members")
    public ApiResponse<Void> deleteMember(@RequestHeader(name = "Member-Id") String memberId) {
            memberService.deleteMember(memberId);
            return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.NO_CONTENT.value()));
    }
}
