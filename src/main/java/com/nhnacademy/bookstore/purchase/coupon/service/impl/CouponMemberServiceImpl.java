package com.nhnacademy.bookstore.purchase.coupon.service.impl;

import com.nhnacademy.bookstore.entity.coupon.Coupon;
import com.nhnacademy.bookstore.entity.coupon.enums.CouponStatus;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import com.nhnacademy.bookstore.purchase.coupon.exception.CouponDoesNotExistException;
import com.nhnacademy.bookstore.purchase.coupon.exception.CouponNotAllowedException;
import com.nhnacademy.bookstore.purchase.coupon.feign.CouponControllerClient;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.request.CreateCouponFormRequest;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.request.ReadCouponFormRequest;
import com.nhnacademy.bookstore.purchase.coupon.feign.dto.response.ReadCouponFormResponse;
import com.nhnacademy.bookstore.purchase.coupon.repository.CouponRepository;
import com.nhnacademy.bookstore.purchase.coupon.service.CouponMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
@RequiredArgsConstructor
public class CouponMemberServiceImpl implements CouponMemberService {
    private final CouponControllerClient couponControllerClient;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ReadCouponFormResponse> readMemberCoupons(Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(MemberNotExistsException::new);

        List<Coupon> coupons = couponRepository.findByMember(member);

        List<Long> couponFormIds = new ArrayList<>();

        for (Coupon c : coupons) {
            couponFormIds.add(c.getCouponFormId());
        }

        return couponControllerClient
                .readCouponForm(ReadCouponFormRequest.builder().couponFormIds(couponFormIds).build())
                .getBody()
                .getData();
    }

    @Override
    public Long useCoupons(Long couponId, Long memberId) {
        Coupon coupon = couponRepository
                .findById(couponId)
                .orElseThrow(()-> new CouponDoesNotExistException(couponId + "쿠폰이 존재하지않습니다."));

        if (Objects.equals(coupon.getMember().getId(), memberId)) {
            throw new CouponNotAllowedException(couponId + "쿠폰이 권한이 없습니다.");
        }

        couponRepository.updateCouponStatus(CouponStatus.USED, couponId);

        return couponId;
    }

    //TODO : 환불 구현시 필요
    @Override
    public Long refundCoupons(Long couponId, Long memberId) {

        return couponId;
    }

    @Async
    @Scheduled(cron = "0 0 13 * * ?")
    @Override
    public void issueBirthdayCoupon() {
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            if (member.getBirthday().getDayOfYear() == ZonedDateTime.now().getDayOfYear()) {
                CreateCouponFormRequest couponFormRequest = CreateCouponFormRequest.builder()
                        .startDate(ZonedDateTime.now())
                        .endDate(ZonedDateTime.now().plusDays(30))
                        .name("Birthday Coupon")
                        .maxPrice(100000)
                        .minPrice(10000)
                        .couponTypeId(2L)
                        .couponUsageId(2L)
                        .build();

                couponControllerClient.createCouponForm(couponFormRequest);

                Long couponFormId = couponControllerClient.createCouponForm(couponFormRequest).getBody().getData();

                Coupon coupon = new Coupon(
                        couponFormId,
                        CouponStatus.READY,
                        member
                );

                couponRepository.save(coupon);
            }
        }
    }

    @Override
    public void issueWelcomeCoupon(Member member) {
        CreateCouponFormRequest couponFormRequest = CreateCouponFormRequest.builder()
                .startDate(ZonedDateTime.now())
                .endDate(ZonedDateTime.now().plusDays(365))
                .name("Welcome Coupon")
                .maxPrice(100000)
                .minPrice(10000)
                .couponTypeId(3L)
                .couponUsageId(2L)
                .build();

        couponControllerClient.createCouponForm(couponFormRequest);

        Long couponFormId = couponControllerClient.createCouponForm(couponFormRequest).getBody().getData();

        Coupon coupon = new Coupon(
                couponFormId,
                CouponStatus.READY,
                member
        );

        couponRepository.save(coupon);
    }
}
