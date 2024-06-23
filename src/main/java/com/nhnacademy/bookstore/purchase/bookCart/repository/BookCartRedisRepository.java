package com.nhnacademy.bookstore.purchase.bookCart.repository;

import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;

import java.util.List;

/**
 * 도서장바구니 레디스 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface BookCartRedisRepository {
    public Long create(String hashName, Long id, ReadBookCartGuestResponse readBookCartGuestResponse);
    public Long update(String hashName, Long id , int quantity);
    public Long delete(String hashName, Long id);
    public List<ReadBookCartGuestResponse> readAllHashName(String hashName);
    public boolean isHit(String hashName);
    public boolean isMiss(String hashName);
    public void loadData(List<ReadBookCartGuestResponse> bookCartGuestResponses,String hashName);
}
