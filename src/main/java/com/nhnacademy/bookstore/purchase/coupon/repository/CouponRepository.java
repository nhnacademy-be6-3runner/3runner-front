package com.nhnacademy.bookstore.purchase.coupon.repository;
import com.nhnacademy.bookstore.entity.coupon.Coupon;
import com.nhnacademy.bookstore.entity.coupon.enums.CouponStatus;
import com.nhnacademy.bookstore.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * jpa 쿠폰 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByMember(Member member);

    @Modifying
    @Transactional
    @Query(value = "UPDATE coupon SET coupon_status = :couponStatus WHERE id = :id LIMIT 1", nativeQuery = true)
    int updateCouponStatus(@Param("couponStatus") CouponStatus couponStatus, @Param("id") Long id);
}