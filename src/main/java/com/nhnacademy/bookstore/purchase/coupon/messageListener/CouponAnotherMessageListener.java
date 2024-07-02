package com.nhnacademy.bookstore.purchase.coupon.messageListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstore.entity.coupon.Coupon;
import com.nhnacademy.bookstore.entity.memberMessage.MemberMessage;
import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import com.nhnacademy.bookstore.purchase.coupon.dto.CouponResponse;
import com.nhnacademy.bookstore.purchase.coupon.repository.CouponCustomRepository;
import com.nhnacademy.bookstore.purchase.memberMessage.dto.CouponFormDto;
import com.nhnacademy.bookstore.purchase.memberMessage.service.MemberMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 만료3일남은 쿠폰 리스너.
 *
 * @author 김병우
 */
@Component
@RequiredArgsConstructor
public class CouponAnotherMessageListener {
    private static final String queueName2 = "3RUNNER-COUPON-EXPIRED-IN-THREE-DAY";
    private final CouponCustomRepository couponCustomRepository;
    private final MemberMessageService memberMessageService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    /**
     * 메시지 받기.
     *
     * @param couponFormDtosJson 쿠폰폼 dto
     */
    @RabbitListener(queues = queueName2)
    public void receiveMessage(String couponFormDtosJson) throws JsonProcessingException {
        List<CouponFormDto> couponFormDtos = objectMapper
                .readValue(couponFormDtosJson, objectMapper.getTypeFactory().constructCollectionType(List.class, CouponFormDto.class));
        List<Long> couponFormIds = new ArrayList<>();
        for (CouponFormDto dto : couponFormDtos) {
            couponFormIds.add(dto.id());
        }

        List<CouponResponse> responses = couponCustomRepository.findMemberIdsByCouponFormIds(couponFormIds);
        for (CouponResponse response : responses) {
            memberMessageService.createMessage(
                    new MemberMessage("회원님 쿠폰 " + response.couponId() + "가 만료 3일 남았습니다.",
                            memberRepository
                                    .findById(response.memberId())
                                    .orElseThrow(MemberNotExistsException::new))
            );
        }
    }
}